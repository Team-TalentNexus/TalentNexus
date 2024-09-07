package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.pojo.Resume;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;

import java.util.Objects;

@RestController
@RequestMapping("/seeker/resume")
@CrossOrigin(origins = "http://localhost:8082")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @PostMapping
    public ApiResponse<Integer> createResume(@RequestBody ResumeBasicDto resumeBasicDto, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!Objects.equals(role, ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        resumeBasicDto.setJobSeekerId(currentId);

        if(resumeService.createResume(resumeBasicDto)) {
            return ApiResponse.success(resumeBasicDto.getId());
        }
        return ApiResponse.error(500, "创建简历失败");
    }

    @GetMapping
    public ApiResponse<Resume> getResume(HttpServletRequest request) {
        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);
        Resume resume = resumeService.getResumeByJobSeekerId(currentId);
        if(resume == null) {
            return ApiResponse.error(404, "简历未创建");
        }
        JobSeeker jobSeeker = jobSeekerService.getByUsername(currentUsername);
        ApiResponse.fillResume(resume, jobSeeker);
        return ApiResponse.success(resume);
    }

    @DeleteMapping
    public ApiResponse<String> deleteResume(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);
        if(resumeService.deleteResumeByJobSeekerId(currentId)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "删除简历失败");
    }
}
