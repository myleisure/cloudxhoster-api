package com.cloudxhoster.api.Repository;

import com.cloudxhoster.api.domain.CountryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CountryRepository implements PanacheRepository<CountryEntity> {
}
