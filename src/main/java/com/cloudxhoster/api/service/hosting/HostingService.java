package com.cloudxhoster.api.service.hosting;

import com.cloudxhoster.api.model.request.HostingForm;
import com.cloudxhoster.api.exception.GenericException;
import com.cloudxhoster.api.model.request.HostingRequest;

/**
 * Service for hosting
 */
public interface HostingService {

    /**
     * Host a website.
     * @param requestBody
     * @throws GenericException
     */
    void host(HostingForm requestBody)
            throws GenericException;

    /**
     * Host a website.
     * @param hostingRequest
     * @throws GenericException
     */
    void host(HostingRequest hostingRequest)
        throws GenericException;
}
