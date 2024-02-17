package org.psw_isa.psw_isa_backend.plugins;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface Plugin {
    void initialize();
    void execute();
    String getVueComponentCode(String widgetName);
    ResponseEntity<?> handleRequest(HttpServletRequest request);
}
