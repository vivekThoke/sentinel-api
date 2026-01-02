package com.project.sentinel_api.controller;

import com.project.sentinel_api.model.Client;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/ping")
    public String ping(HttpServletRequest request){
        Client client = (Client) request.getAttribute("client");

        return "Authenticated from request from client " + client.getName();
    }
}
