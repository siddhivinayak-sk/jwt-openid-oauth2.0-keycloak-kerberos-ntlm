package com.sk.auth.provider.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;


public class CustomUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator, UserQueryProvider {
    
    private static final Logger log = Logger.getLogger(CustomUserStorageProvider.class.getName());
    private KeycloakSession ksession;
    private ComponentModel model;

    public CustomUserStorageProvider(KeycloakSession ksession, ComponentModel model) {
        this.ksession = ksession;
        this.model = model;
    }

    @Override
    public void close() {
        log.info("[I30] close()");
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        log.log(Level.INFO, "[I35] getUserById(" + id + ")");
        StorageId sid = new StorageId(id);
        return getUserByUsername(sid.getExternalId(),realm);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        log.log(Level.INFO,"[I41] getUserByUsername(" + username + ")");
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select id, username, firstName,lastName, email, birthDate from users where username = ?");
            st.setString(1, username);
            st.execute();
            ResultSet rs = st.getResultSet();
            if ( rs.next()) {
                return mapUser(realm,rs);
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        log.log(Level.INFO, "[I48] getUserByEmail(" + email + ")");
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select id, username, firstName,lastName, email, birthDate from users where email = ?");
            st.setString(1, email);
            st.execute();
            ResultSet rs = st.getResultSet();
            if ( rs.next()) {
                return mapUser(realm,rs);
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        log.log(Level.INFO, "[I57] supportsCredentialType(" + credentialType + ")");
        return PasswordCredentialModel.TYPE.endsWith(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        log.log(Level.INFO, "[I57] isConfiguredFor(realm=" + realm.getName() + ",user=" + user.getUsername() + ",credentialType=" + credentialType + ")");
        // In our case, password is the only type of credential, so we allways return 'true' if
        // this is the credentialType
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        log.log(Level.INFO, "[I57] isValid(realm=" + realm.getName() + ",user=" + user.getUsername() + ",credentialInput.type=" + credentialInput.getType() + ")");
        if( !this.supportsCredentialType(credentialInput.getType())) {
            return false;
        }
        StorageId sid = new StorageId(user.getId());
        String username = sid.getExternalId();
        
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select password from users where username = ?");
            st.setString(1, username);
            st.execute();
            ResultSet rs = st.getResultSet();
            if ( rs.next()) {
                String pwd = rs.getString(1);
                return pwd.equals(credentialInput.getChallengeResponse());
            }
            else {
                return false;
            }
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    // UserQueryProvider implementation
    
    @Override
    public int getUsersCount(RealmModel realm) {
        log.log(Level.INFO, "[I93] getUsersCount: realm=" + realm.getName() + "");
        try ( Connection c = DbUtil.getConnection(this.model)) {
            Statement st = c.createStatement();
            st.execute("select count(*) from users");
            ResultSet rs = st.getResultSet();
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {
        return getUsers(realm,0, 5000); // Keep a reasonable maxResults 
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
        log.log(Level.INFO, "[I113] getUsers: realm=" + realm.getName() + "");
        
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select id, username, firstName,lastName, email, birthDate from users order by username offset ? rows fetch next ? rows only");
            st.setInt(2, maxResults);
            st.setInt(1, firstResult);
            st.execute();
            ResultSet rs = st.getResultSet();
            List<UserModel> users = new ArrayList<>();
            while(rs.next()) {
                users.add(mapUser(realm,rs));
            }
            return users;
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        return searchForUser(search,realm,0,5000);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
        log.log(Level.INFO, "[I139] searchForUser: realm=" + realm.getName() + "");
        
        try ( Connection c = DbUtil.getConnection(this.model)) {
            PreparedStatement st = c.prepareStatement("select id, username, firstName,lastName, email, birthDate from users where username like ? order by username offset ? rows fetch next ? rows only");
            st.setString(1, "%" + search + "%");
            st.setInt(3, maxResults);
            st.setInt(2, firstResult);
            st.execute();
            ResultSet rs = st.getResultSet();
            List<UserModel> users = new ArrayList<>();
            while(rs.next()) {
                users.add(mapUser(realm,rs));
            }
            return users;
        }
        catch(SQLException ex) {
            throw new RuntimeException("Database error:" + ex.getMessage(),ex);
        }
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
    	log.log(Level.INFO, "[I201] searchForUser: param=" + params + "");
        return searchForUser(params,realm,0,5000);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
    	log.log(Level.INFO, "[I202] searchForUser: param=" + params + "");
        return getUsers(realm, firstResult, maxResults);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
    	log.log(Level.INFO, "[I203] getGroupMembers: realm=" + realm + "");
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
    	log.log(Level.INFO, "[I204] getGroupMembers: realm=" + realm + "");
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
    	log.log(Level.INFO, "[I205] searchForUserByUserAttribute: attribute=" + attrName + " value" + attrValue);
        return Collections.emptyList();
    }

    
    //------------------- Implementation 
    private UserModel mapUser(RealmModel realm, ResultSet rs) throws SQLException {
    	log.log(Level.INFO, "mapUser");
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        CustomUser user = new CustomUser.Builder(ksession, realm, model, rs.getString("username"))
          .email(rs.getString("email"))
          .firstName(rs.getString("firstName"))
          .lastName(rs.getString("lastName"))
          .birthDate(rs.getDate("birthDate"))
          .build();

        log.log(Level.INFO, "Entity:" + user.getId() + " " + user.getUsername());
        return user;
    }
}