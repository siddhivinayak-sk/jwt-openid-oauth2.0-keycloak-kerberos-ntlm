package com.sk.auth.provider.user;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.adapter.AbstractUserAdapter;


class CustomUser extends AbstractUserAdapter {
    
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Date birthDate;

    private CustomUser(KeycloakSession session, RealmModel realm,
      ComponentModel storageProviderModel,
      String username,
      String email,
      String firstName,
      String lastName,
      Date birthDate ) {
        super(session, realm, storageProviderModel);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public String getId() {
        return "f:" + storageProviderModel.getId() + ":" + username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    
    @Override
    public Map<String, List<String>> getAttributes() {
        HashMap<String, List<String>> attributes = new HashMap<>();
        attributes.put(UserModel.USERNAME, Arrays.asList(getUsername()));
        attributes.put(UserModel.EMAIL, Arrays.asList(getEmail()));
        attributes.put(UserModel.FIRST_NAME, Arrays.asList(getFirstName()));
        attributes.put(UserModel.LAST_NAME, Arrays.asList(getLastName()));
        attributes.put("birthDate", Arrays.asList(getBirthDate().toString()));
        return attributes;
    }

    static class Builder {
        private final KeycloakSession session;
        private final RealmModel realm;
        private final ComponentModel storageProviderModel;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private Date birthDate;
        
        Builder(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel,String username) {
            this.session = session;
            this.realm = realm;
            this.storageProviderModel = storageProviderModel;
            this.username = username;
        }
        
        CustomUser.Builder email(String email) {
            this.email = email;
            return this;
        }
        
        CustomUser.Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        CustomUser.Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        CustomUser.Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }
        
        CustomUser build() {
            return new CustomUser(
              session,
              realm,
              storageProviderModel,
              username,
              email,
              firstName,
              lastName,
              birthDate);
            
        }
    }
}