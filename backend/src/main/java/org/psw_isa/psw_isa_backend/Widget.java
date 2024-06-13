package org.psw_isa.psw_isa_backend;

import java.util.HashMap;

import javax.security.auth.login.AppConfigurationEntry;

import org.psw_isa.psw_isa_backend.models.FormItem;

public interface Widget {
    void decodeAnswer(String answer); 

    String getTypeName(); 

    void save(ApplicationContextProvider provider); 

    Widget populateFromFormItem(ApplicationContextProvider provider, FormItem item);

    void populateFromData(ApplicationContextProvider provider, HashMap<String, String> data);

    void setItem(FormItem item);

    FormItem getItem();

    void setAnswer(String answer);

    String getAnswer();

    Long getId();

    HashMap<String, String> getData();

    void delete(ApplicationContextProvider provider, FormItem item);
}
