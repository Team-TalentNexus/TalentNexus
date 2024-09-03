package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.JobApplication;
import org.thirtysix.talentnexus.service.JobApplicationService;
import org.thirtysix.talentnexus.service.JobPositionService;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;

import java.util.List;

@RestController
@RequestMapping("/seeker/apply")
@CrossOrigin(origins = "http://localhost:8082")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobPositionService jobPositionService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/{job_position_id}")
    public ApiResponse<String> submitApplication(@PathVariable("job_position_id") Integer jobPositionId, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }
        // 判断是否存在该职位
        String title = jobPositionService.getTitleById(jobPositionId);
        if(title == null) {
            return ApiResponse.error(404, "该职位不存在或已失效");
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobPositionId(jobPositionId);
        jobApplication.setStatus("已投递");

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);
        jobApplication.setJobSeekerId(currentId);

        // 获取简历id
        Integer resumeId = resumeService.getIdByJobSeekerId(currentId);
        jobApplication.setResumeId(resumeId);

        if(jobApplicationService.submitApplication(jobApplication)) {
            messagingTemplate.convertAndSend("/topic/notifications", "收到新的申请，职位: " + title);
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "提交申请失败");
    }

    @GetMapping
    public ApiResponse<List<JobApplication>> getJobApplications(HttpServletRequest request) {
        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        try {
            return ApiResponse.success(jobApplicationService.getApplicationByJobSeekerId(currentId));
        } catch (Exception e) {
            return ApiResponse.error(500, "查询失败");
        }
    }

}
