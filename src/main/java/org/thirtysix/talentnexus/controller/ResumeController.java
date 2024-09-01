package org.thirtysix.talentnexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thirtysix.talentnexus.dto.ResumeBasicDto;
import org.thirtysix.talentnexus.service.ResumeService;
import org.thirtysix.talentnexus.util.ApiResponse;

@RestController
@RequestMapping("/resume")
@CrossOrigin(origins = "http://localhost:8082")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @PostMapping
    public ApiResponse<String> createResume(ResumeBasicDto resumeBasicDto) {
        if(resumeService.createResume(resumeBasicDto)) {
            return ApiResponse.success("");
        }

        return ApiResponse.error(500, "创建简历失败");
    }
}
