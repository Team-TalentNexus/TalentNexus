package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.ProjectExperience;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.service.ProjectExperienceService;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.util.ApiResponse;

import java.util.Objects;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:8082")
public class ProjectExperienceController {
    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ProjectExperienceService projectExperienceService;

    @PostMapping
    public ApiResponse<String> addProject(@RequestBody ProjectExperience projectExperience, HttpServletRequest request) {
        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        Integer resumeId = projectExperience.getResumeId();

        // 根据简历id获取用户id
        Integer jobSeekerId = resumeService.getJobSeekerIdById(resumeId);

        if(!Objects.equals(currentId, jobSeekerId)) {
            return ApiResponse.error(401, "权限认证失败");
        }

        if(projectExperienceService.add(projectExperience)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "添加项目经历失败");
    }
}
