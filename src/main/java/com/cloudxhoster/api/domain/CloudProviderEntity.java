package com.cloudxhoster.api.domain;

import com.cloudxhoster.api.constants.persistence.UniqueConstraintNames;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

@Entity
@Table(name = "cloud_provider",
        uniqueConstraints = {@UniqueConstraint(name = UniqueConstraintNames.CLOUD_PROVIDER_CODE,columnNames = {"code"})})
public class CloudProviderEntity implements Serializable {

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

    @Override
    public String toString() {
        return new StringJoiner(", ", CloudProviderEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("code='" + code + "'")
                .add("name='" + name + "'")
                .add("supported=" + supported)
                .add("creationDate=" + creationDate)
                .toString();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isSupported() { return supported; }

    public void setSupported(boolean supported) { this.supported = supported; }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
}
