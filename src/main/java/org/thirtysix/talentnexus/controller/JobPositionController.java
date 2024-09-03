package org.thirtysix.talentnexus.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.service.CompanyService;
import org.thirtysix.talentnexus.service.JobPositionService;
import org.thirtysix.talentnexus.util.ApiResponse;

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
}
