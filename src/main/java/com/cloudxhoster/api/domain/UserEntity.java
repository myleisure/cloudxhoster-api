package com.cloudxhoster.api.domain;

import com.cloudxhoster.api.constants.persistence.UniqueConstraintNames;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

@Entity
@Table(name = "user", schema = "public",
        uniqueConstraints = {@UniqueConstraint(name = UniqueConstraintNames.USER_EMAIL, columnNames = {"email"})})
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email")
    public String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private boolean verified;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @NotNull
    private CountryEntity country;

    @Override
    public String toString() {
        return new StringJoiner(", ", UserEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email='" + email + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("verified=" + verified)
                .add("creationDate=" + creationDate)
                .add("country=" + country)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity countryEntity) {
        this.country = countryEntity;
    }
}
