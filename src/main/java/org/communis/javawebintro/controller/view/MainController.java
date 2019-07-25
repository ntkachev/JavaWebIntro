package org.communis.javawebintro.controller.view;

import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    private final EntityService entityService;

    @Autowired
    public MainController(EntityService entityService) {
        this.entityService = entityService;
    }

    @RequestMapping(value = {"/badbrowser"}, method = RequestMethod.GET)
    public String badBrowser() {
        return "errors/badBrowser";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getPublicPage(Authentication authentication) throws ServerException {
        entityService.setCurrentToMain();
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

}
