package com.cloudxhoster.api.Repository;

import com.cloudxhoster.api.domain.HostingRegionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HostingRegionRepository implements PanacheRepository<HostingRegionEntity> {
}
