package org.psw_isa.psw_isa_backend.repository;

import java.util.Collection;

import org.psw_isa.psw_isa_backend.models.SystemConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigurationRepository extends CrudRepository<SystemConfiguration, Long>{
    SystemConfiguration save(SystemConfiguration config);
    Collection<SystemConfiguration> findAll(); 
    
}
