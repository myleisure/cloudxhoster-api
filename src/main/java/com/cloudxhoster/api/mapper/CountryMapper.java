package com.cloudxhoster.api.mapper;

import com.cloudxhoster.api.domain.CountryEntity;
import com.cloudxhoster.api.model.Country;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("countryMapper")
public class CountryMapper implements TransFormer<CountryEntity, Country> {

    @Override
    public Country toSingle(CountryEntity item) {

        if (item == null) return null;

        final var country = new Country();
        country.setIso(item.getIso());
        country.setIso3(item.getIso3());
        country.setName(item.getName());
        country.setNiceName(item.getNiceName());
        country.setNumCode(item.getNumCode());
        country.setPhoneCode(item.getPhoneCode());
        return country;
    }
}
