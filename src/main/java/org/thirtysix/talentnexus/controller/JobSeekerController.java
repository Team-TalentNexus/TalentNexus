package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;
import org.thirtysix.talentnexus.util.JwtUtils;

import java.net.http.HttpRequest;

/**
 * 求职者
 */
@RestController
@RequestMapping("/seeker")
@CrossOrigin(origins = "http://localhost:8082")
public class JobSeekerController {
    @Autowired
    private JobSeekerService jobSeekerService;

    /**
     * 求职者登录接口
     * @param loginDto 用户名和密码
     * @return ApiResponse，登录成功data为JWT
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody JobSeekerLoginDto loginDto) {
        if(jobSeekerService.login(loginDto)) {
            String token = JwtUtils.generateToken(loginDto.getUsername(), ConstUtil.SEEKER);
            return ApiResponse.success(token);
        } else {
            return ApiResponse.error(401, "Invalid credentials");
        }
    }

    /**
     * 求职者注册接口
     * @param seeker 求职者用户数据
     * @return 注册结果
     */
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody JobSeeker seeker) {
        System.out.println(seeker.getEmail());
        String res = jobSeekerService.register(seeker);
        return switch (res) {
            case "dup" -> ApiResponse.error(409, "用户名或邮箱已存在");
            case "ok" -> ApiResponse.success("Success");
            case "err" -> ApiResponse.error(500, "服务器内部错误");
            default -> ApiResponse.error(400, res);
        };
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/api/{username}")
    public ApiResponse<JobSeeker> getIdByUsername(@PathVariable String username, HttpServletRequest request) {
        if (username == null || username.trim().isEmpty() || username.trim().equals(" ")) {
            return ApiResponse.error(400, "用户名为空");
        }

        String currentUsername = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");

        // 检查是否在访问他人信息
        if (!username.equals(currentUsername)) {
            return ApiResponse.error(401, "没有权限");
        }

        if (role == null || !role.equals(ConstUtil.SEEKER)) {
            return ApiResponse.error(401, "没有权限");
        }

        JobSeeker seeker = jobSeekerService.getByUsername(username);
        if (seeker != null) {
            return ApiResponse.success(seeker);
        }

        return ApiResponse.error(404, "用户名不存在");
    }

    /**
     * 测试JWT 鉴权
     * @return 任意
     */
    @GetMapping("/test")
    public String test() {
        return "123";
    }
}
