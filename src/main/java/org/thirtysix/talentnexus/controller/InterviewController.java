package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.Interview;
import org.thirtysix.talentnexus.service.InterviewService;
import org.thirtysix.talentnexus.util.ApiResponse;

import java.util.Objects;

@RestController
@RequestMapping("/company/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    /**
     * 创建面试
     * @param interview 面试详情
     * @param request HTTP 请求对象，用于权限验证
     * @return 操作结果
     */
    @PostMapping("/create")
    public ApiResponse<Integer> createInterview(@RequestBody Interview interview, HttpServletRequest request) {
        // 从请求中获取当前用户的角色和公司ID
        String role = (String) request.getAttribute("role");
        Integer currentCompanyId = (Integer) request.getAttribute("companyId");

        // 权限验证：确保当前用户是公司用户
        if (!"COMPANY".equals(role)) {
            return ApiResponse.error(401, "权限认证失败");
        }

        // 验证面试信息中公司ID是否匹配当前用户
        if (!currentCompanyId.equals(interview.getCompanyId())) {
            return ApiResponse.error(403, "无法创建其他公司的面试");
        }

        // 调用服务层方法创建面试
        Integer interviewId = interviewService.createInterview(interview);
        if (interviewId != null) {
            return ApiResponse.success(interviewId);
        }
        return ApiResponse.error(500, "创建面试失败");
    }

    /**
     * 查看面试信息
     * @param id 面试ID
     * @param request HTTP 请求对象，用于权限验证
     * @return 面试信息
     */
    @GetMapping("/view/{id}")
    public ApiResponse<Interview> getInterview(@PathVariable Integer id, HttpServletRequest request) {
        // 从请求中获取当前用户的角色和公司ID
        String role = (String) request.getAttribute("role");
        Integer currentCompanyId = (Integer) request.getAttribute("companyId");

        // 获取面试信息
        Interview interview = interviewService.getInterviewById(id);
        if (interview == null) {
            return ApiResponse.error(404, "面试记录不存在");
        }

        // 权限验证：确保当前用户是公司用户
        if (!"COMPANY".equals(role)) {
            return ApiResponse.error(401, "权限认证失败");
        }

        // 验证面试信息中公司ID是否匹配当前用户
        if (!currentCompanyId.equals(interview.getCompanyId())) {
            return ApiResponse.error(403, "无法查看其他公司的面试信息");
        }

        return ApiResponse.success(interview);
    }

    /**
     * 修改面试信息
     * @param interview 修改后的面试信息
     * @param request HTTP 请求对象，用于权限验证
     * @return 操作结果
     */
    @PutMapping("/update")
    public ApiResponse<String> updateInterview(@RequestBody Interview interview, HttpServletRequest request) {
        // 从请求中获取当前用户的角色和公司ID
        String role = (String) request.getAttribute("role");
        Integer currentCompanyId = (Integer) request.getAttribute("companyId");

        // 权限验证：确保当前用户是公司用户
        if (!"COMPANY".equals(role)) {
            return ApiResponse.error(401, "权限认证失败");
        }

        // 验证面试信息中公司ID是否匹配当前用户
        if (!currentCompanyId.equals(interview.getCompanyId())) {
            return ApiResponse.error(403, "无法修改其他公司的面试");
        }

        // 调用服务层方法更新面试信息
        boolean updated = interviewService.updateInterview(interview);
        if (updated) {
            return ApiResponse.success("面试信息已更新");
        }
        return ApiResponse.error(500, "更新面试信息失败");
    }

    /**
     * 删除面试
     * @param id 面试ID
     * @param request HTTP 请求对象，用于权限验证
     * @return 操作结果
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteInterview(@PathVariable Integer id, HttpServletRequest request) {
        // 从请求中获取当前用户的角色和公司ID
        String role = (String) request.getAttribute("role");
        Integer currentCompanyId = (Integer) request.getAttribute("companyId");

        // 获取面试信息
        Interview interview = interviewService.getInterviewById(id);
        if (interview == null) {
            return ApiResponse.error(404, "面试记录不存在");
        }

        // 权限验证：确保当前用户是公司用户
        if (!"COMPANY".equals(role)) {
            return ApiResponse.error(401, "权限认证失败");
        }

        // 验证面试信息中公司ID是否匹配当前用户
        if (!currentCompanyId.equals(interview.getCompanyId())) {
            return ApiResponse.error(403, "无法删除其他公司的面试");
        }

        // 调用服务层方法删除面试
        boolean deleted = interviewService.deleteInterview(id);
        if (deleted) {
            return ApiResponse.success("面试已删除");
        }
        return ApiResponse.error(500, "删除面试失败");
    }
}



