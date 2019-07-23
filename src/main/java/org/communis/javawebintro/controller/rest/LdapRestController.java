package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.LdapAuthWrapper;
import org.communis.javawebintro.dto.LdapGroupInfo;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/ldap")
public class LdapRestController {

    private final LdapService ldapService;

    @Autowired
    public LdapRestController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Long add(LdapAuthWrapper ldapAuth, HttpServletResponse response) throws ServerException, IOException {
        response.sendRedirect("/admin/ldap/");
        return ldapService.add(ldapAuth);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public void edit(LdapAuthWrapper ldapAuth, HttpServletResponse response) throws ServerException, IOException {
        response.sendRedirect("/admin/ldap/");
        ldapService.edit(ldapAuth);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<LdapAuthWrapper> getAll() throws ServerException {
        return ldapService.getAllActive();
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public List<String> getGroups(LdapAuthWrapper wrapper) throws ServerException {
        return ldapService.getGroups(wrapper);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public List<LdapGroupInfo> testConnection(LdapAuthWrapper wrapper) throws ServerException {
        return ldapService.getGroupsInfo(wrapper);
    }

    @RequestMapping(value = "/{id}/activate", method = RequestMethod.POST)
    public void activate(@PathVariable("id") Long id) throws ServerException {
        ldapService.activate(id);
    }

    @RequestMapping(value = "/{id}/deactivate", method = RequestMethod.POST)
    public void deactivate(@PathVariable("id") Long id) throws ServerException {
        ldapService.deactivate(id);
    }
}
