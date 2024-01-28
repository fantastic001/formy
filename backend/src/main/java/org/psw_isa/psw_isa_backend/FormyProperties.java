package org.psw_isa.psw_isa_backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "formy")
public class FormyProperties {
    private boolean onlyAuthenticatedUsersCanUploadImages;

    public boolean getOnlyAuthenticatedUsersCanUploadImages() {
        return onlyAuthenticatedUsersCanUploadImages;
    }
    
}
