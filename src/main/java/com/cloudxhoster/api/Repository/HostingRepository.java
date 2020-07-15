package com.cloudxhoster.api.Repository;

import com.cloudxhoster.api.domain.HostingEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HostingRepository implements PanacheRepository<HostingEntity> {
}
