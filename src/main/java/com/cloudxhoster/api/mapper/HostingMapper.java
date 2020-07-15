package com.cloudxhoster.api.mapper;

import com.cloudxhoster.api.domain.HostingEntity;
import com.cloudxhoster.api.domain.HostingRegionEntity;
import com.cloudxhoster.api.domain.WebsiteEntity;
import com.cloudxhoster.api.model.Hosting;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Objects;

@ApplicationScoped
@Named("hostingMapper")
public class HostingMapper implements TransFormer<HostingEntity, Hosting> {

    @Override
    public Hosting toSingle(HostingEntity item) {

        if (item == null) return null;

        final var hosting = new Hosting();

        WebsiteEntity websiteEntity = item.getWebsite();
        Objects.requireNonNull(websiteEntity, "User has to have a website");
        hosting.setName(websiteEntity.getName());


        HostingRegionEntity hostingRegionEntity = item.getHostingRegion();
        Objects.requireNonNull(hostingRegionEntity, "Hosting region must not be null");
        hosting.setRegion(hostingRegionEntity.getName());

        hosting.setDomainName(item.getDomainName());
        hosting.setHostingDate(item.getCreationDate());
        return hosting;
    }
}
