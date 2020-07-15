package com.cloudxhoster.api.Repository;

import com.cloudxhoster.api.domain.CloudProviderEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CloudProviderRepository implements PanacheRepository<CloudProviderEntity> {
}
