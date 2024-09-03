package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.ProjectExperience;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.service.ProjectExperienceService;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;

import java.util.Objects;

@RestController
@RequestMapping("/seeker/project")
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
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        // 根据用户id获取简历id
        Integer resumeId = resumeService.getIdByJobSeekerId(currentId);
        projectExperience.setResumeId(resumeId);

        if(projectExperienceService.add(projectExperience)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "添加项目经历失败");
    }
}
