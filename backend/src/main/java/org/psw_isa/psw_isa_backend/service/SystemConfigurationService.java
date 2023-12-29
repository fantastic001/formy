package org.psw_isa.psw_isa_backend.service;

import java.util.Collection;

import org.jvnet.hk2.annotations.Service;
import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.models.SystemConfiguration;
import org.psw_isa.psw_isa_backend.repository.SystemConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Service
public class SystemConfigurationService {
    @Autowired
    SystemConfigurationRepository repository; 

    private void save(SystemConfiguration config) {
        int size = repository.findAll().size();
        Assert.state(size <= 1, "Multiple system configuration entities");
        if (size > 0) {
            // we are updating configuration 
            Logger.getInstance().info("Updating system configuration");
            repository.save(config);

        } else {
            Logger.getInstance().info("Creating new system configuration");
        }
    }

    public SystemConfiguration get() {
        Collection<SystemConfiguration> config = repository.findAll();
        if (config.size() == 0) {
            return new SystemConfiguration();
        } else {
            return config.stream().findFirst().get();
        }
    }
}
