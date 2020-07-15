package com.cloudxhoster.api.domain;

import com.cloudxhoster.api.enums.HostingStatusEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

@Entity
@Table(name = "hosting")
public class HostingEntity implements Serializable {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "website_id")
    @EmbeddedId
    private WebsiteEntity website;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hosting_region_id")
    @EmbeddedId
    private HostingRegionEntity hostingRegion;

    @NotNull
    @Column(name = "domain_name")
    private String domainName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Type(type = "com.cloudxhoster.api.converter.EnumTypePostgreSql")
    private HostingStatusEnum status;

    @Column(name = "creation_date")
    @NotNull
    private Date creationDate;

    @Column(name = "last_update")
    @NotNull
    private Date lastUpdate;

    @Override
    public String toString() {
        return new StringJoiner(", ", HostingEntity.class.getSimpleName() + "[", "]")
                .add("website=" + website)
                .add("hostingRegion=" + hostingRegion)
                .add("domainName='" + domainName + "'")
                .add("status=" + status)
                .add("creationDate=" + creationDate)
                .add("lastUpdate=" + lastUpdate)
                .toString();
    }

    public WebsiteEntity getWebsite() { return website; }

    public void setWebsite(WebsiteEntity websiteEntity) { this.website = websiteEntity; }

    public HostingRegionEntity getHostingRegion() { return hostingRegion; }

    public void setHostingRegion(HostingRegionEntity hostingRegionEntity) { this.hostingRegion = hostingRegionEntity; }

    public String getDomainName() { return domainName; }

    public void setDomainName(String domainName) { this.domainName = domainName; }

    public HostingStatusEnum getStatus() { return status; }

    public void setStatus(HostingStatusEnum status) { this.status = status; }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public Date getLastUpdate() { return lastUpdate; }

    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
}
