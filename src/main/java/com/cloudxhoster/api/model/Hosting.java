package com.cloudxhoster.api.model;

import java.util.Date;
import java.util.StringJoiner;

public class Hosting {

    private String name;
    private String domainName;
    private String region;
    private Date hostingDate;

    @Override
    public String toString() {
        return new StringJoiner(", ", Hosting.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("domainName='" + domainName + "'")
                .add("region='" + region + "'")
                .add("hostingDate=" + hostingDate)
                .toString();
    }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDomainName() { return domainName; }

    public void setDomainName(String domainName) { this.domainName = domainName; }

    public Date getHostingDate() { return hostingDate; }

    public void setHostingDate(Date hostingDate) { this.hostingDate = hostingDate; }
}
