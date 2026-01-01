package com.project.sentinel_api.repository;

import com.project.sentinel_api.model.RiskAssessment;
import com.project.sentinel_api.model.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long> {
    Optional<RiskAssessment> findTopByClientIdOrderByAssessedAtDesc(Long clientId);
}
