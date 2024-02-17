package org.psw_isa.psw_isa_backend.plugins;

public interface Plugin {
    void initialize();
    void execute();
    String getVueComponentCode();
}
