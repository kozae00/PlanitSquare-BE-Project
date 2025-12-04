package com.example.planitsquarebeproject.domain.country.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Country {

    @Id
    private String countryCode;

    private String countryName;
}
