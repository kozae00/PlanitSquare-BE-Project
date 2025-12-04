package com.example.planitsquarebeproject.domain.country.repository;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, String> {
}
