package com.cloudxhoster.api.domain;

import com.cloudxhoster.api.constants.persistence.UniqueConstraintNames;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

@Entity
@Table(name = "hosting_region",
        uniqueConstraints = {@UniqueConstraint(name = UniqueConstraintNames.HOSTING_REGION_ENTITY_UNIQUE_CONSTRAINT_MANE,
                columnNames = {"cloud_provider_id", "code", "name"})})
public class HostingRegionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private boolean supported;

    @NotNull
    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne
    @JoinColumn(name = "cloud_provider_id")
    @NotNull
    private CloudProviderEntity cloudProvider;

    @Override
    public String toString() {
        return new StringJoiner(", ", HostingRegionEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("code='" + code + "'")
                .add("name='" + name + "'")
                .add("supported=" + supported)
                .add("creationDate=" + creationDate)
                .add("cloudProvider=" + cloudProvider)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSupported() {
        return supported;
    }

    public void setSupported(boolean supported) {
        this.supported = supported;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CloudProviderEntity getCloudProvider() {
        return cloudProvider;
    }

    public void setCloudProvider(CloudProviderEntity cloudProviderEntity) {
        this.cloudProvider = cloudProviderEntity;
    }
}
