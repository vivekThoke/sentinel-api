package com.project.sentinel_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "risk_assessments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskAssessment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    private int riskScore; // 0-100

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @Column(length = 512)
    private String reason;

    private Instant assessedAt;

}
