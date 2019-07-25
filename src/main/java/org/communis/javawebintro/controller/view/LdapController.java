package org.communis.javawebintro.controller.view;


import org.communis.javawebintro.dto.LdapAuthWrapper;
import org.communis.javawebintro.dto.filters.LdapAuthFilterWrapper;
import org.communis.javawebintro.enums.UserRole;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.EntityService;
import org.communis.javawebintro.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/admin/ldap")
public class LdapController {

    private String LDAP_VIEW_PATH = "admin/ldap/";

    private final LdapService ldapService;
    private final EntityService entityService;

    @Autowired
    public LdapController(LdapService ldapService, EntityService entityService) {
        this.ldapService = ldapService;
        this.entityService = entityService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getLdapListPage(Pageable pageable, LdapAuthFilterWrapper filter) throws ServerException {
        entityService.setCurrentToLdapList();
        ModelAndView ldapListPage = new ModelAndView(LDAP_VIEW_PATH + "list");
        ldapListPage.addObject("page", ldapService.getPageByFilter(pageable, filter));
        ldapListPage.addObject("filter", filter);
        return ldapListPage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() throws ServerException {
        LdapAuthWrapper ldap = new LdapAuthWrapper();
        entityService.setCurrentToLdapAddition(ldap);
        return addFromWrapper(ldap);
    }

     public ModelAndView addFromWrapper(LdapAuthWrapper ldap) {
        ModelAndView addPage = new ModelAndView(LDAP_VIEW_PATH + "add");
        addPage.addObject("ldap", ldap);
        addPage.addObject("roles", UserRole.values());
        return addPage;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) throws ServerException {
        LdapAuthWrapper ldap = ldapService.getForEdit(id);
        entityService.setCurrentToLdapEditing(ldap);
        return editFromWrapper(ldap);
    }

    public ModelAndView editFromWrapper(LdapAuthWrapper ldap) {
        ModelAndView editPage = new ModelAndView(LDAP_VIEW_PATH + "edit");
        editPage.addObject("ldap", ldap);
        editPage.addObject("roles", UserRole.values());
        return editPage;
    }
}
