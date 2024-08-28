package org.thirtysix.talentnexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thirtysix.talentnexus.service.HelloService;

@RestController
@RequestMapping("/hello")
@CrossOrigin(origins = "http://localhost:8082")
public class HelloController {
    @Autowired
    HelloService helloService;

    @GetMapping("/{id}")
    public String get(@PathVariable int id) {
        return helloService.getMessage(id);
    }
}
