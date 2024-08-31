package org.thirtysix.talentnexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.utl.ApiResponse;
import org.thirtysix.talentnexus.utl.ConstUtil;
import org.thirtysix.talentnexus.utl.JwtUtils;

/**
 * 求职者
 */
@RestController
@RequestMapping("/seeker")
@CrossOrigin(origins = "http://localhost:8082")
public class JobSeekerController {
    @Autowired
    JobSeekerService jobSeekerService;

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
     * 测试JWT 鉴权
     * @return 任意
     */
    @GetMapping("/test")
    public String test() {
        return "123";
    }
}
