package com.cloudxhoster.api.Repository;

import com.cloudxhoster.api.domain.WebsiteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebsiteRepository implements PanacheRepository<WebsiteEntity> {
}
