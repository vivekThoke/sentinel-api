package com.project.sentinel_api.controller;

import com.project.sentinel_api.analyzer.RiskAnalyzer;
import com.project.sentinel_api.model.Client;
import com.project.sentinel_api.model.RiskAssessment;
import com.project.sentinel_api.repository.RiskAssessmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/risk")
@RequiredArgsConstructor
public class RiskController {
    private final RiskAnalyzer riskAnalyzer;
    private final RiskAssessmentRepository riskAssessmentRepository;

    @GetMapping("/current")
    public RiskAssessment getCurrentRisk(HttpServletRequest request){
        Client client = (Client) request.getAttribute("client");

        RiskAssessment assessment =
                riskAnalyzer.analyze(client.getId());

        return riskAssessmentRepository.save(assessment);
    }

}
