package com.cloudxhoster.api.model;

import java.util.StringJoiner;

public class Country {

    public String iso;
    public String name;
    public String niceName;
    public String iso3;
    public Integer numCode;
    public Integer phoneCode;

    @Override
    public String toString() {
        return new StringJoiner(", ", Country.class.getSimpleName() + "[", "]")
                .add("iso='" + iso + "'")
                .add("name='" + name + "'")
                .add("niceName='" + niceName + "'")
                .add("iso3='" + iso3 + "'")
                .add("numCode=" + numCode)
                .add("phoneCode=" + phoneCode)
                .toString();
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

    public int getNumCode() { return numCode; }

    public void setNumCode(Integer numCode) { this.numCode = numCode; }

    public int getPhoneCode() { return phoneCode; }

    public void setPhoneCode(Integer phoneCode) { this.phoneCode = phoneCode; }
}
