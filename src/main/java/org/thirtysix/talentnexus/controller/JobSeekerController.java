package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.dto.JobPositionDto;
import org.thirtysix.talentnexus.dto.JobSeekerLoginDto;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.pojo.JobSeeker;
import org.thirtysix.talentnexus.service.CompanyService;
import org.thirtysix.talentnexus.service.JobPositionService;
import org.thirtysix.talentnexus.service.JobSeekerService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;
import org.thirtysix.talentnexus.util.JwtUtils;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 求职者
 */
@RestController
@RequestMapping("/seeker")
@CrossOrigin(origins = "http://localhost:8082")
public class JobSeekerController {
    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private JobPositionService jobPositionService;

    @Autowired
    private CompanyService companyService;

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
     * @return 用户信息
     */
    @GetMapping("/api/info")
    public ApiResponse<JobSeeker> getIdByUsername(HttpServletRequest request) {
        String currentUsername = (String) request.getAttribute("username");

        JobSeeker seeker = jobSeekerService.getByUsername(currentUsername);
        if (seeker != null) {
            return ApiResponse.success(seeker);
        }

        return ApiResponse.error(404, "用户名不存在");
    }

    @GetMapping("/position")
    public ApiResponse<List<JobPositionDto>> getPositions(@RequestParam Integer page, @RequestParam Integer size, HttpServletRequest request) {
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

        List<JobPosition> positions = jobPositionService.getAll(page, size);

        List<JobPositionDto> jobPositionDtos = new ArrayList<>();
        for (JobPosition position : positions) {
            String companyName = companyService.getCompanyNameById(position.getCompanyId());

            JobPositionDto dto = new JobPositionDto();
            dto.setId(position.getId());
            dto.setCompanyName(companyName);
            dto.setTitle(position.getTitle());
            dto.setDescription(position.getDescription());
            dto.setLocation(position.getLocation());
            dto.setEmploymentType(position.getEmploymentType());
            dto.setSalaryRange(position.getSalaryRange());
            dto.setActive(position.isActive());

            jobPositionDtos.add(dto);
        }

        return ApiResponse.success(jobPositionDtos);
    }
}
