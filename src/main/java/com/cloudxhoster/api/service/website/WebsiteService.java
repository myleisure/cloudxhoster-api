package com.cloudxhoster.api.service.website;

import com.cloudxhoster.api.domain.WebsiteEntity;
import com.cloudxhoster.api.exception.GenericException;

public interface WebsiteService {

    /**
     * Host a website in a bucket
     *
     * @param domain the domain name of the website
     * @param region the hosting region
     * @param srcDir the website's files directory path
     */
    void hostWebsiteInBucket(String domain, String region, String srcDir);

    /**
     * Save website in the database.
     * @param websiteName
     * @param userId
     * @return
     * @throws GenericException
     */
    WebsiteEntity save(String websiteName, Long userId) throws GenericException;

    /**
     *
     * @param websiteName
     */
    void delete(String websiteName);
}
