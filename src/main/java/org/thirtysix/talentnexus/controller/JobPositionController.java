package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.service.CompanyService;
import org.thirtysix.talentnexus.service.JobPositionService;
import org.thirtysix.talentnexus.util.ApiResponse;
import org.thirtysix.talentnexus.util.ConstUtil;

import java.util.Objects;

@RestController
@RequestMapping("/company/position")
@CrossOrigin(origins = "http://localhost:8082")
public class JobPositionController {
    @Autowired
    private JobPositionService jobPositionService;

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ApiResponse<String> addJobPosition(@RequestBody JobPosition jobPosition, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.COMPANY)) {
            return ApiResponse.error(401, "没有权限");
        }

        if(jobPosition == null) {
            return ApiResponse.error(400, "Bad Request");
        }
        String username = (String) request.getAttribute("username");
        Integer currentId = companyService.getCompanyIdByUsername(username);

        jobPosition.setCompanyId(currentId);

        if(jobPositionService.addAddJobPosition(jobPosition)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "添加职位失败");
    }

    @DeleteMapping("/{position_id}")
    public ApiResponse<String> deletePosition(@PathVariable("position_id") Integer positionId, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if(!role.equals(ConstUtil.COMPANY)) {
            return ApiResponse.error(401, "没有权限");
        }

        String username = (String) request.getAttribute("username");
        Integer currentId = companyService.getCompanyIdByUsername(username);

        Integer companyId = jobPositionService.getCompanyIdById(positionId);

        if(!Objects.equals(currentId, companyId)) {
            return ApiResponse.error(401, "没有权限");
        }

        if(jobPositionService.deleteById(positionId)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "删除职位失败");
    }
}
