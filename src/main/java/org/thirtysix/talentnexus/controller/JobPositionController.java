package org.thirtysix.talentnexus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.pojo.JobPosition;
import org.thirtysix.talentnexus.service.JobPositionService;

@RestController
@RequestMapping("/company/position")
@CrossOrigin(origins = "http://localhost:8082")
public class JobPositionController {
    @Autowired
    private JobPositionService jobPositionService;

    @PostMapping
    public void addJobPosition(@RequestBody JobPosition jobPosition) {

    }
}
