package org.psw_isa.psw_isa_backend;

import javax.security.auth.login.AppConfigurationEntry;

import org.psw_isa.psw_isa_backend.models.FormItem;

public interface Widget {
    void decodeAnswer(String answer); 

    String getTypeName(); 

    void save(ApplicationContextProvider provider); 

    Widget populateFromFormItem(ApplicationContextProvider provider, FormItem item); 
}
