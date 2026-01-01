package com.project.sentinel_api.repository;

import com.project.sentinel_api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepoistory extends JpaRepository<Client, Long> {
    Optional<Client> findByApiKey(String apikey);
}
