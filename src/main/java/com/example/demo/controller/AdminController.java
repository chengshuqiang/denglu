package com.example.demo.controller;

import com.example.demo.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public String login(@RequestBody String json) {
        return adminService.login(json);
    }

    @PostMapping(value = "/register", produces = {"application/json;charset=UTF-8"})
    public String registerAdmin(@RequestBody String json) {
        return adminService.registerAdmin(json);
    }

}
