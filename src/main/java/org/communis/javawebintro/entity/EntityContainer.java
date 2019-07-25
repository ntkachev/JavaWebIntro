package org.communis.javawebintro.entity;

import lombok.Data;
import lombok.Getter;
import org.communis.javawebintro.dto.LdapAuthWrapper;
import org.communis.javawebintro.dto.UserWrapper;

import java.io.Serializable;

//@Data
public class EntityContainer implements Serializable {

    public enum Type {
        MAIN("Main page"), PERSONAL("Personal page"),
        USER_ADD("User addition"), USER_EDIT("User editing"),
        LDAPAUTH_ADD("LdapAuth addition"), LDAPAUTH_EDIT("LdapAuth editing"),
        USERLIST("User list"), LDAPLIST("LDAP server list");

        @Getter
        private final String friendlyName;

        Type(String friendlyName) {
            this.friendlyName = friendlyName;
        }
    }

//    public static class InvalidTypeConstructionException extends Exception {
//
//        private final Type got;
//
//        public InvalidTypeConstructionException(Type got) {
//            this.got = got;
//        }
//
//        @Override
//        public String toString() {
//            return "Attempting to create entity of type " + got + " without an accompanying object";
//        }
//    }

//    public static class InvalidTypeAccessException extends Exception {
//
//        private final Type expected;
//        private final Type got;
//
//        InvalidTypeAccessException(Type expected, Type got) {
//            this.expected = expected;
//            this.got = got;
//        }
//
//        @Override
//        public String toString() {
//            return "Attempting to get entity of type " + expected + "when the entity's type is " + got;
//        }
//    }

    @Getter
    private Type type;
    private Object entity;

    public EntityContainer() {

    }

    public EntityContainer(UserWrapper user, boolean isPersonal) {
        if (user.getId() == null) {
            type = Type.USER_ADD;
        } else {
            type = isPersonal ? Type.PERSONAL : Type.USER_EDIT;
        }
        entity = user;
    }

    public EntityContainer(LdapAuthWrapper auth) {
        if (auth.getId() == null) {
            type = Type.LDAPAUTH_ADD;
        } else {
            type = Type.LDAPAUTH_EDIT;
        }
        entity = auth;
    }

    public EntityContainer(Type type) {
        if (type == Type.MAIN || type == Type.LDAPLIST || type == Type.USERLIST) {
            this.type = type;
            entity = null;
        } else {
//            throw new InvalidTypeConstructionException(type);
            this.type = Type.MAIN;
            entity = null;
        }

    }

    public UserWrapper getUser() {
        if (type == Type.USER_ADD || type == Type.USER_EDIT || type == Type.PERSONAL) {
            return (UserWrapper)entity;
        } else {
//            throw new InvalidTypeAccessException(Type.USER, type);
            return null;
        }
    }

    public LdapAuthWrapper getLdapAuth() {
        if (type == Type.LDAPAUTH_ADD || type == Type.LDAPAUTH_EDIT) {
            return (LdapAuthWrapper)entity;
        } else {
//            throw new InvalidTypeAccessException(Type.LDAPAUTH, type);
            return null;
        }
    }

    public String toString() {
        switch(type) {
            case PERSONAL:
                return "Персональные данные";
            case USER_ADD: {
                UserWrapper user = (UserWrapper)entity;
                String displayName = user.getFio();
                if (displayName.isEmpty()) {
                    return "Добавление пользователя";
                } else {
                    return displayName;
                }
            }
            case USER_EDIT: {
                UserWrapper user = (UserWrapper)entity;
                String displayName = user.getFio();
                if (displayName.isEmpty()) {
                    return "Редактирование пользователя";
                } else {
                    return displayName;
                }
            }
            case LDAPAUTH_ADD: {
                LdapAuthWrapper ldap = (LdapAuthWrapper)entity;
                String displayName = ldap.getName();
                if (displayName.isEmpty()) {
                    return "Добавление LDAP-сервера";
                } else {
                    return displayName;
                }
            }
            case LDAPAUTH_EDIT: {
                LdapAuthWrapper ldap = (LdapAuthWrapper)entity;
                String displayName = ldap.getName();
                if (displayName.isEmpty()) {
                    return "Изменение LDAP-сервера";
                } else {
                    return displayName;
                }
            }
            case MAIN:
                return "Главная страница";
            case LDAPLIST:
                return "Реестр LDAP-серверов";
            case USERLIST:
                return "Реестр пользователей";
        }
        return null;
    }

}
