package com.driver.repository;

import com.driver.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Integer> {
    Optional<ServiceProvider> findByName(String providerName);
}
