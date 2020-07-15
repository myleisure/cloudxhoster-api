package com.cloudxhoster.api.model;

import java.time.Instant;
import java.util.Date;

public class Bucket {

    private final String name;
    private final Date creationDate;

    public Bucket(String name, Instant creationDate) {
        this.name = name;
        this.creationDate = Date.from(creationDate);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BucketDto{");
        sb.append("name='").append(name).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append('}');
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
