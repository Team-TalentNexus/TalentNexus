package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.JobApplication;
import org.thirtysix.talentnexus.service.*;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;

import java.util.List;

@RestController
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

    @Autowired
    private CompanyService companyService;

    @PostMapping("/seeker/apply/{job_position_id}")
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

    @GetMapping("/seeker/apply")
    public ApiResponse<List<JobApplication>> getJobApplications(@RequestParam("page") Integer page, @RequestParam("size") Integer size, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        // 验证 page 和 size 是否存在并且是正整数
        if (page == null || size == null || page <= 0 || size <= 0) {
            return ApiResponse.error(400, "Bad Request: 'page' and 'size' must be positive integers.");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        try {
            return ApiResponse.success(jobApplicationService.getApplicationByJobSeekerId(currentId, page, size));
        } catch (Exception e) {
            return ApiResponse.error(500, "查询失败");
        }
    }

    @GetMapping("/seeker/apply/count")
    public ApiResponse<Integer> getActiveApplicationNum(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = jobSeekerService.getIdByUsername(currentUsername);

        return ApiResponse.success(jobApplicationService.getActiveApplicationNumByJobSeekerId(currentId));
    }

    @GetMapping("/company/apply")
    public ApiResponse<List<JobApplication>> companyGetApplications(@RequestParam("page") Integer page, @RequestParam("size") Integer size, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.COMPANY)) {
            return ApiResponse.error(401, "没有权限");
        }

        // 验证 page 和 size 是否存在并且是正整数
        if (page == null || size == null || page <= 0 || size <= 0) {
            return ApiResponse.error(400, "Bad Request: 'page' and 'size' must be positive integers.");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = companyService.getCompanyIdByUsername(currentUsername);

        try {
            return ApiResponse.success(jobApplicationService.getApplicationsByCompanyId(currentId, page, size));
        } catch (Exception e) {
            return ApiResponse.error(500, "查询失败");
        }
    }

    @GetMapping("/company/apply/count")
    public ApiResponse<Integer> companyGetApplicationsCount(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.COMPANY)) {
            return ApiResponse.error(401, "没有权限");
        }

        String currentUsername = (String) request.getAttribute("username");
        Integer currentId = companyService.getCompanyIdByUsername(currentUsername);

        return ApiResponse.success(jobApplicationService.getActiveApplicationNumByCompanyId(currentId));
    }
}
