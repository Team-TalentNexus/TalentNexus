package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.WorkExperience;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.service.WorkExperienceService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;

import java.util.Objects;

@RestController
@RequestMapping("/seeker/work")
@CrossOrigin(origins = "http://localhost:8082")
public class WorkExperienceController {
    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private WorkExperienceService workExperienceService;

    @PostMapping
    public ApiResponse<String> addWorkExperience(@RequestBody WorkExperience workExperience, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        Integer resumeId = workExperience.getResumeId();

        // 根据简历id获取用户id
        Integer jobSeekerId = resumeService.getJobSeekerIdById(resumeId);

        if(!Objects.equals(currentId, jobSeekerId)) {
            return ApiResponse.error(401, "权限认证失败");
        }

        if(workExperienceService.add(workExperience)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "添加工作经历失败");
    }
}
