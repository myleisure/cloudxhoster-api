package com.cloudxhoster.api.Repository;

import com.cloudxhoster.api.domain.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

}
