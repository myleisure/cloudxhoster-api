package com.cloudxhoster.api.constants.persistence;

public class UniqueConstraintNames {
    private UniqueConstraintNames() {
    }

    public static final String USER_EMAIL = "user_email_key";
    public static final String CLOUD_PROVIDER_CODE = "cloud_provider_code_key";
    public static final String HOSTING_REGION_ENTITY_UNIQUE_CONSTRAINT_MANE = "hosting_region_cloud_provider_id_code_name_key";
    public static final String WEBSITE_ENTITY_NAME = "website_name_key";
}
