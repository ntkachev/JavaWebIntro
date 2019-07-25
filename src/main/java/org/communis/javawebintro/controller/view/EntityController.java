package org.communis.javawebintro.controller.view;

import org.communis.javawebintro.dto.filters.LdapAuthFilterWrapper;
import org.communis.javawebintro.dto.filters.UserFilterWrapper;
import org.communis.javawebintro.entity.EntityContainer;
import org.communis.javawebintro.exception.ServerException;
import org.communis.javawebintro.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "entities")
//@SessionAttributes("entityId")
public class EntityController {

    private final EntityService entityService;
    private final UserController userController;
    private final PersonalController personalController;
    private final LdapController ldapController;
    private final MainController mainController;

    @Autowired
    public EntityController(EntityService entityService, UserController userController,
                            PersonalController personalController, LdapController ldapController, MainController mainController) {
        this.entityService = entityService;
        this.userController = userController;
        this.personalController = personalController;
        this.ldapController = ldapController;
        this.mainController = mainController;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView swapToEntity(@PathVariable("id") int id, Authentication authentication,
                                     Pageable pageable, UserFilterWrapper filterUserWrapper,
                                     LdapAuthFilterWrapper authFilterWrapper) throws ServerException {

        EntityContainer entity = entityService.getEntityById(id);
        if (entity == null) {
            entityService.setEntity(id, new EntityContainer(EntityContainer.Type.MAIN));
        }
        entityService.setCurrentEntityId(id);
        switch (entity.getType()) {
            case USERLIST:
                return userController.list(pageable, filterUserWrapper);
            case USER_EDIT:
                return userController.editPageFromWrapper(entity.getUser());
            case USER_ADD:
                return userController.addPageFromWrapper(entity.getUser());
            case PERSONAL:
                return personalController.getPersonalPageFromWrapper(entity.getUser());
            case LDAPLIST:
                return ldapController.getLdapListPage(pageable, authFilterWrapper);
            case LDAPAUTH_ADD:
                return ldapController.addFromWrapper(entity.getLdapAuth());
            case LDAPAUTH_EDIT:
                return ldapController.editFromWrapper(entity.getLdapAuth());
            case MAIN:
                String url = mainController.getPublicPage(authentication);
                return new ModelAndView("redirect:/" + url);
            default:
                return null;
        }
    }
}
