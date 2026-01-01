package com.project.sentinel_api.analyzer;

import com.project.sentinel_api.model.RiskAssessment;
import com.project.sentinel_api.model.RiskLevel;
import com.project.sentinel_api.repository.ApiRequestEventRepoistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiskAnalyzer {
    private final ApiRequestEventRepoistory apiRequestEventRepoistory;

    public RiskAssessment analyze(Long clientId){
         Instant windowStart = Instant.now().minus(1, ChronoUnit.MINUTES);

         int score = 0;

         List<String> reasons = new ArrayList<>();

         long requestCount = apiRequestEventRepoistory.countByClientIdAndTimestampAfter(clientId, windowStart);

         if (requestCount > 30){
             score += 40;
             reasons.add("High request rate");
         }

         long distinctIp = apiRequestEventRepoistory.countDistinctIps(clientId, windowStart);

         if (distinctIp > 10){
             score += 30;
             reasons.add("High IP diversity");
         }

         long distinctEndpoint = apiRequestEventRepoistory.countDistinctEndpoints(clientId, windowStart);

         if (distinctEndpoint > 15){
             score += 30;
             reasons.add("End point scanning behaviour");
         }

         score = Math.min(score, 100);

        RiskLevel riskLevel = score >= 70 ? RiskLevel.HIGH :
                                score >= 40 ? RiskLevel.MEDIUM :
                                        RiskLevel.LOW;

        return RiskAssessment.builder()
                .clientId(clientId)
                .riskScore(score)
                .riskLevel(riskLevel)
                .reason(String.join(", ", reasons))
                .assessedAt(Instant.now())
                .build();


    }
}
