package com.cloudxhoster.api.model.request;

import com.cloudxhoster.api.enums.BusinessErrorCodeEnum;
import com.cloudxhoster.api.exception.GenericException;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.StringJoiner;

public class HostingForm {

    @FormParam("domainName")
    private String domainName;

    @FormParam("websiteName")
    private String websiteName;

    @FormParam("region")
    private String region;

    @FormParam("userId")
    private String userId;



    @FormParam("archive")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream file;

    @Override
    public String toString() {
        return new StringJoiner(", ", HostingForm.class.getSimpleName() + "[", "]")
                .add("domainName='" + domainName + "'")
                .add("websiteName='" + websiteName + "'")
                .add("region='" + region + "'")
                .add("userId=" + userId)
                .add("file=" + file)
                .toString();
    }

    public String getDomainName() { return domainName; }

    public void setDomainName(String domainName) { this.domainName = domainName; }

    public String getWebsiteName() { return websiteName; }

    public void setWebsiteName(String websiteName) { this.websiteName = websiteName; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public InputStream getFile() { return file; }

    public void setFile(InputStream file) { this.file = file; }

    public Long getUserIdasLong() throws GenericException {
        try {
            return Long.parseLong(userId);
        } catch(NumberFormatException e) {
            throw new GenericException("User ID input invalid: " + e.getMessage(), BusinessErrorCodeEnum.ITEM_NOT_FOUND);
        }
    }
}
