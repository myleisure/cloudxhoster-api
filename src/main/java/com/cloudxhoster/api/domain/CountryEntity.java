package com.cloudxhoster.api.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iso")
    @NotNull
    private String iso;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "nice_name")
    @NotNull
    private String niceName;

    @Column(name = "iso3")
    public String iso3;

    @Column(name = "num_code")
    private Integer numCode;

    @Column(name = "phone_code")
    @NotNull
    private Integer phoneCode;

    @Override
    public String toString() {
        return new StringJoiner(", ", CountryEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("iso='" + iso + "'")
                .add("name='" + name + "'")
                .add("niceName='" + niceName + "'")
                .add("iso3='" + iso3 + "'")
                .add("numCode=" + numCode)
                .add("phoneCode=" + phoneCode)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }
}
