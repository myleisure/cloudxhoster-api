package com.cloudxhoster.api.domain;

import com.cloudxhoster.api.constants.persistence.UniqueConstraintNames;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

@Entity
@Table(name = "website",
        uniqueConstraints = {@UniqueConstraint(name = UniqueConstraintNames.WEBSITE_ENTITY_NAME, columnNames = {"name"})})
public class WebsiteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public WebsiteEntity() {
    }

    public WebsiteEntity(String name, UserEntity user, Date creationDate) {
        this.name = name;
        this.user = user;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WebsiteEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("creationDate=" + creationDate)
                .add("user=" + user)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userEntity) {
        this.user = userEntity;
    }
}
