package com.project.sentinel_api.repository;

import com.project.sentinel_api.model.ApiRequestEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface ApiRequestEventRepoistory extends JpaRepository<ApiRequestEvent, Long> {

    long countByClientIdAndTimestampAfter(Long clientId, Instant since);

    @Query("""
        SELECT COUNT(DISTINCT e.ipAddress)
        FROM ApiRequestEvent e
        WHERE e.clientId = :clientId
        AND e.timestamp > :since
    """)
    long countDistinctIps(Long clientId, Instant since);

    @Query("""
        SELECT COUNT(DISTINCT e.endPoint)
        FROM ApiRequestEvent e
        where e.clientId = :clientId
        AND e.timestamp > :since
    """)
    long countDistinctEndpoints(Long clientId, Instant since);

}
