package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.util.ApiResponse;

import java.util.Objects;

@RestController
@RequestMapping("/resume")
@CrossOrigin(origins = "http://localhost:8082")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @PostMapping
    public ApiResponse<String> createResume(@RequestBody ResumeBasicDto resumeBasicDto, HttpServletRequest request) {
        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);
        if(!Objects.equals(currentId, resumeBasicDto.getJobSeekerId())) {
            return ApiResponse.error(401, "权限认证失败");
        }

        if(resumeService.createResume(resumeBasicDto)) {
            return ApiResponse.success("");
        }
        return ApiResponse.error(500, "创建简历失败");
    }
}
