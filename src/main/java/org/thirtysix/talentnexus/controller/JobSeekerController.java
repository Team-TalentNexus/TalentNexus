package org.thirtysix.talentnexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
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
     * 测试JWT 鉴权
     * @return 任意
     */
    @GetMapping("/test")
    public String test() {
        return "123";
    }
}
