package com.example.planitsquarebeproject.domain.country.repository;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
