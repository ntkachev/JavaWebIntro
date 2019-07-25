package org.communis.javawebintro.service;

import lombok.Data;
import org.communis.javawebintro.dto.LdapAuthWrapper;
import org.communis.javawebintro.dto.UserWrapper;
import org.communis.javawebintro.entity.EntityContainer;
import org.communis.javawebintro.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.util.concurrent.Callable;

@Service
public class EntityService {

    private UserService userService;

    @Scope("session")
    @Data
    public static class SessionEntities {
        private int id;
        private EntityContainer[] entities;

        public SessionEntities() {
            id = 1;
            entities = new EntityContainer[]{
                    new EntityContainer(EntityContainer.Type.MAIN),
                    new EntityContainer(EntityContainer.Type.MAIN)
            };
        }
    }

    private SessionEntities sessionEntities;

    public void setCurrentEntityId(int id) {
        sessionEntities.setId(id);
    }

    @Bean("entityId")
    public Callable<Integer> getEntityId() {
        return () -> sessionEntities.getId();
    }

    public int getCurrentEntityId() {
        return sessionEntities.getId();
    }

    @Autowired
    public EntityService(UserService userService) {
        this.userService = userService;
        sessionEntities = new SessionEntities();
    }

    @Bean("entities")
    public Callable<EntityContainer[]> getCurrentUserEntities() throws ServerException {
        return () -> {
            return sessionEntities.getEntities();
        };
    }

    public EntityContainer getEntityById(int id) throws ServerException {
        return sessionEntities.getEntities()[id-1];
    }

    public void setEntity(int id, EntityContainer container) throws ServerException {
        sessionEntities.getEntities()[id-1] = container;
    }

    public void setSessionEntities(EntityContainer container) throws ServerException {
        setEntity(getCurrentEntityId(), container);
    }

    public void setCurrentToUserAddition(UserWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper, false);
        setSessionEntities(container);
    }

    public void setCurrentToUserEditing(UserWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper, false);
        setSessionEntities(container);
    }

    public void setCurrentToPersonal(UserWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper, true);
        setSessionEntities(container);
    }

    public void setCurrentToLdapAddition(LdapAuthWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper);
        setSessionEntities(container);
    }

    public void setCurrentToLdapEditing(LdapAuthWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper);
        setSessionEntities(container);
    }

    public void setCurrentToUserList() throws ServerException {
        EntityContainer container = new EntityContainer(EntityContainer.Type.USERLIST);
        setSessionEntities(container);
    }

    public void setCurrentToLdapList() throws ServerException  {
        EntityContainer container = new EntityContainer(EntityContainer.Type.LDAPLIST);
        setSessionEntities(container);
    }
    public void setCurrentToMain() throws ServerException {
        EntityContainer container = new EntityContainer(EntityContainer.Type.MAIN);
        setSessionEntities(container);
    }
}
