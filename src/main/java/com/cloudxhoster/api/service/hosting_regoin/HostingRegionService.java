package com.cloudxhoster.api.service.hosting_regoin;

import com.cloudxhoster.api.domain.HostingRegionEntity;
import com.cloudxhoster.api.exception.GenericException;

/**
 * Hosting region service.
 */
public interface HostingRegionService {
    /**
     * Get region by name.
     * @param name
     * @return
     */
    HostingRegionEntity getHostingRegion(String name) throws GenericException;
}
