package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.CompanyLoginDto;
import org.thirtysix.talentnexus.pojo.Company;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.CompanyService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;
import org.thirtysix.talentnexus.util.JwtUtils;

/**
 * 公司
 */
@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:8082")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    /**
     * 公司登录接口
     * @param loginDto 公司用户名和密码
     * @return ApiResponse，登录成功data为JWT
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody CompanyLoginDto loginDto) {
        if(companyService.login(loginDto)) {
            String token = JwtUtils.generateToken(loginDto.getUsername(), ConstUtil.COMPANY);
            return ApiResponse.success(token);
        } else {
            return ApiResponse.error(401, "Invalid credentials");
        }
    }

    /**
     * 公司注册接口
     * @param company 公司用户数据
     * @return 注册结果
     */
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody Company company) {
        String res = companyService.register(company);
        return switch (res) {
            case "dup" -> ApiResponse.error(409, "用户名或邮箱已存在");
            case "ok" -> ApiResponse.success("Success");
            case "err" -> ApiResponse.error(500, "服务器内部错误");
            default -> ApiResponse.error(400, res);
        };
    }

    /**
     * 根据用户名获取公司信息
     * @return 公司信息
     */
    @GetMapping("/api/info")
    public ApiResponse<Company> getIdByUsername(HttpServletRequest request) {
        String currentUsername = (String) request.getAttribute("username");

        Company company = companyService.getByUsername(currentUsername);
        if (company != null) {
            return ApiResponse.success(company);
        }

        return ApiResponse.error(404, "用户名不存在");
    }

    /**
     * 测试JWT 鉴权
     * @return 任意
     */
    @GetMapping("/test")
    public String test() {
        return "456";
    }
}
