package com.cloudxhoster.api.service.hosting_regoin;

import com.cloudxhoster.api.Repository.HostingRegionRepository;
import com.cloudxhoster.api.domain.HostingRegionEntity;
import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HostingRegionServiceImpl implements HostingRegionService {

    private static final Logger LOG = LoggerFactory.getLogger(HostingRegionServiceImpl.class);

    private final HostingRegionRepository hostingRegionRepository;

    @Inject
    public HostingRegionServiceImpl(HostingRegionRepository hostingRegionRepository) {
        this.hostingRegionRepository = hostingRegionRepository;
    }


    @Override
    public HostingRegionEntity getHostingRegion(String name) throws GenericException {
        return this.hostingRegionRepository.find("from HostingRegion where name=?1", name)
                .firstResultOptional()
                .orElseThrow(() -> {
                    LOG.error("[CloudxHosterAPI] Hosting region with name : {} is not in the database", name);
                    return new GenericException("Hosting region with name " + name + " is not found", BusinessErrorCodeEnum.ITEM_NOT_FOUND);
                });
    }
}
