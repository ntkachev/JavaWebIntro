package org.communis.javawebintro.controller.rest;

import org.communis.javawebintro.dto.LdapAuthWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.entity.EntityContainer;
import org.communis.javawebintro.entity.LdapAuth;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "entities")
//@SessionAttributes("entityId")
public class EntityRestController {

    private final EntityService entityService;

    @Autowired
    public EntityRestController(EntityService entityService) {
        this.entityService = entityService;
    }

    @RequestMapping(value = "/user_add", method = RequestMethod.POST)
    public void setCurrentToUserAddition(UserWrapper user) throws ServerException {
        entityService.setCurrentToUserAddition(user);
    }

    @RequestMapping(value = "/user_edit", method = RequestMethod.POST)
    public void setCurrentToUserEditing(UserWrapper user) throws ServerException {
        entityService.setCurrentToUserEditing(user);
    }

    @RequestMapping(value = "/ldap_add", method = RequestMethod.POST)
    public void setCurrentToLdapAddition(LdapAuthWrapper ldap) throws ServerException {
        entityService.setCurrentToLdapAddition(ldap);
    }

    @RequestMapping(value = "/ldap_edit", method = RequestMethod.POST)
    public void setCurrentToLdapEditing(LdapAuthWrapper ldap) throws ServerException {
        entityService.setCurrentToLdapEditing(ldap);
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    public void setCurrentToPersonal(UserWrapper user) throws ServerException {
        entityService.setCurrentToPersonal(user);
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.POST)
    public void setCurrentToUserList() throws ServerException {
        entityService.setCurrentToUserList();
    }

    @RequestMapping(value = "/ldaplist", method = RequestMethod.POST)
    public void setCurrentToLdapList() throws ServerException {
        entityService.setCurrentToLdapList();
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public void setCurrentToMain() throws ServerException {
        entityService.setCurrentToMain();
    }

//    @RequestMapping(value = "/current", method = RequestMethod.POST)
//    public void updateEntity(EntityContainer container)//, @ModelAttribute("entityId") int entityId)
//            throws ServerException {
//        entityService.setEntity(entityService.getCurrentEntityId(), container);
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void updateEntity(int id, EntityContainer container) throws ServerException {
//        entityService.setEntity(id, container);
//    }
}
