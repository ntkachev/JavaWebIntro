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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Service
public class EntityService {

    private UserService userService;
    private Map<Long, EntityContainer[]> entities;

    @Scope("session")
    @Data
    public static class CurrentEntity {
        private int id = 1;
    }

    private CurrentEntity currentEntity = new CurrentEntity();

    public void setCurrentEntityId(int id) {
        currentEntity.setId(id);
    }

    @Bean("entityId")
    public Callable<Integer> getEntityId() {
        return () -> currentEntity.getId();
    }

    public int getCurrentEntityId() {
        return currentEntity.getId();
    }

    @Autowired
    public EntityService(UserService userService) {
        this.userService = userService;
        entities = new HashMap<>();
    }

    @Bean("entities")
    public Callable<EntityContainer[]> getCurrentUserEntities() throws ServerException {
        return () -> {
            Long userId = userService.getCurrentUser().getId();
            ensureUserHasEntities(userId);
            return entities.get(userId);
        };
    }

    public EntityContainer getEntityById(int id) throws ServerException {
        Long userId = userService.getCurrentUser().getId();
        ensureUserHasEntities(userId);
        return entities.get(userId)[id-1];
    }

    public void setEntity(int id, EntityContainer container) throws ServerException {
        Long userId = userService.getCurrentUser().getId();
        ensureUserHasEntities(userId);
        entities.get(userId)[id-1] = container;
    }

    public void setCurrentEntity(EntityContainer container) throws ServerException {
        setEntity(getCurrentEntityId(), container);
    }

    public void setCurrentToUserAddition(UserWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper, false);
        setCurrentEntity(container);
    }

    public void setCurrentToUserEditing(UserWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper, false);
        setCurrentEntity(container);
    }

    public void setCurrentToPersonal(UserWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper, true);
        setCurrentEntity(container);
    }

    public void setCurrentToLdapAddition(LdapAuthWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper);
        setCurrentEntity(container);
    }

    public void setCurrentToLdapEditing(LdapAuthWrapper wrapper) throws ServerException {
        EntityContainer container = new EntityContainer(wrapper);
        setCurrentEntity(container);
    }

    public void setCurrentToUserList() throws ServerException {
        EntityContainer container = new EntityContainer(EntityContainer.Type.USERLIST);
        setCurrentEntity(container);
    }

    public void setCurrentToLdapList() throws ServerException  {
        EntityContainer container = new EntityContainer(EntityContainer.Type.LDAPLIST);
        setCurrentEntity(container);
    }
    public void setCurrentToMain() throws ServerException {
        EntityContainer container = new EntityContainer(EntityContainer.Type.MAIN);
        setCurrentEntity(container);
    }

    private void ensureUserHasEntities(Long userId) {
        if (!entities.containsKey(userId)) {
            entities.put(userId, new EntityContainer[] {
                    new EntityContainer(EntityContainer.Type.MAIN),
                    new EntityContainer(EntityContainer.Type.MAIN)
            });
        }
    }
}
