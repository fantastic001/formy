package org.psw_isa.psw_isa_backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class FormyConfiguration {

    @Bean
    public static ApplicationContextProvider contextProvider() {
        return new ApplicationContextProvider();
    }

    @Value("${MEDIA_DIR:/var/data/formy/media}")
    private String mediaDir;

    public String getMediaDir() {
        return mediaDir;
    }

    @Bean
    public JavaMailSender javaMailSender() { 
          return new JavaMailSenderImpl();
    }

    // plgin directory
    @Value("${PLUGIN_DIR:/var/data/formy/plugins}")
    private String pluginDir;

    public String getPluginDir() {
        return pluginDir;
    }


}
