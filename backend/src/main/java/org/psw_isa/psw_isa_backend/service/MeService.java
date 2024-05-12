package org.psw_isa.psw_isa_backend.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import javax.servlet.http.HttpSession;

import org.psw_isa.psw_isa_backend.Logger;
import org.psw_isa.psw_isa_backend.models.User;

@RestController
public class MeService {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public User greeting() {
        // get session 
	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); 
    	HttpSession session = attr.getRequest().getSession(true); 
	    String username = (String) session.getAttribute("user");
        Logger.getInstance().debug("MeService.greeting username: " + username);
        return userService.findOneByemail(username);
    }
}
