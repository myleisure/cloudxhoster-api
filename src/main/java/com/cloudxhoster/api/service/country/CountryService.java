package com.cloudxhoster.api.service.country;


import com.cloudxhoster.api.Repository.CountryRepository;
import com.cloudxhoster.api.domain.CountryEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CountryService {

    @Inject
    private CountryRepository countryRepository;

    public List<CountryEntity> getCountries() {
        return countryRepository.listAll();
    }

    public Optional<CountryEntity> getCountry(Long countryId) {
        return this.countryRepository.findByIdOptional(countryId);
    }
}
