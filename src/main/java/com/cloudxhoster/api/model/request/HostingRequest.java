package com.cloudxhoster.api.model.request;

import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;

public class HostingRequest {

    private String domainName;
    private String websiteName;
    private String region;
    private String userId;
    private String archiveUrl;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HostingRequest{");
        sb.append("domainName='").append(domainName).append('\'');
        sb.append(", websiteName='").append(websiteName).append('\'');
        sb.append(", region='").append(region).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", archiveUrl='").append(archiveUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArchiveUrl() {
        return archiveUrl;
    }

    public void setArchiveUrl(String archiveUrl) {
        this.archiveUrl = archiveUrl;
    }

    public Long getUserIdasLong() throws GenericException {
        try {
            return Long.parseLong(userId);
        } catch(NumberFormatException e) {
            throw new GenericException("User ID input invalid: " + e.getMessage(), BusinessErrorCodeEnum.ITEM_NOT_FOUND);
        }
    }
}
