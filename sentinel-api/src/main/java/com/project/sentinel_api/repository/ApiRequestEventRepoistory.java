package com.project.sentinel_api.repository;

import com.project.sentinel_api.model.ApiRequestEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRequestEventRepoistory extends JpaRepository<ApiRequestEvent, Long> {
}
