package org.psw_isa.psw_isa_backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class FormyProperties {
    @Value("${formy.only-authenticated-users-can-upload-images:true}")
    private boolean onlyAuthenticatedUsersCanUploadImages;

    public boolean getOnlyAuthenticatedUsersCanUploadImages() {
        return onlyAuthenticatedUsersCanUploadImages;
    }
    
}
