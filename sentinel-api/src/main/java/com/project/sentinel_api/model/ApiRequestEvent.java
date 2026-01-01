package com.project.sentinel_api.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "api_request_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiRequestEvent {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    private String endPoint;

    private String httpMethod;

    private String idAddress;

    @Column(length = 512)
    private String userAgent;

    private Instant timestamp;
}
