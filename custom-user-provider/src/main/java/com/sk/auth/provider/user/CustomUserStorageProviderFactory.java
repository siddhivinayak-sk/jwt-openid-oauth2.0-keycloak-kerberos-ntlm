package com.sk.auth.provider.user;

import static com.sk.auth.provider.user.CustomUserStorageProviderConstants.CONFIG_KEY_DB_PASSWORD;
import static com.sk.auth.provider.user.CustomUserStorageProviderConstants.CONFIG_KEY_DB_USERNAME;
import static com.sk.auth.provider.user.CustomUserStorageProviderConstants.CONFIG_KEY_JDBC_DRIVER;
import static com.sk.auth.provider.user.CustomUserStorageProviderConstants.CONFIG_KEY_JDBC_URL;
import static com.sk.auth.provider.user.CustomUserStorageProviderConstants.CONFIG_KEY_VALIDATION_QUERY;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

public class CustomUserStorageProviderFactory implements UserStorageProviderFactory<CustomUserStorageProvider> {
    private static final Logger log = Logger.getLogger(CustomUserStorageProviderFactory.class.getName());    
    protected final List<ProviderConfigProperty> configMetadata;
    
    public CustomUserStorageProviderFactory() {
        log.log(Level.INFO, "[I24] CustomUserStorageProviderFactory created");
        
        
        // Create config metadata
        configMetadata = ProviderConfigurationBuilder.create()
          .property()
            .name(CONFIG_KEY_JDBC_DRIVER)
            .label("JDBC Driver Class")
            .type(ProviderConfigProperty.STRING_TYPE)
            //.defaultValue("org.h2.Driver")
            .defaultValue("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            .helpText("Fully qualified class name of the JDBC driver")
            .add()
          .property()
            .name(CONFIG_KEY_JDBC_URL)
            .label("JDBC URL")
            .type(ProviderConfigProperty.STRING_TYPE)
            //.defaultValue("jdbc:h2:mem:customdb")
            .defaultValue("jdbc:sqlserver://localhost:1433;databaseName=keycloak")
            .helpText("JDBC URL used to connect to the user database")
            .add()
          .property()
            .name(CONFIG_KEY_DB_USERNAME)
            .label("Database User")
            .type(ProviderConfigProperty.STRING_TYPE)
            .defaultValue("sa")
            .helpText("Username used to connect to the database")
            .add()
          .property()
            .name(CONFIG_KEY_DB_PASSWORD)
            .label("Database Password")
            .type(ProviderConfigProperty.STRING_TYPE)
            .defaultValue("p@ssw0rd")
            .helpText("Password used to connect to the database")
            .secret(true)
            .add()
          .property()
            .name(CONFIG_KEY_VALIDATION_QUERY)
            .label("SQL Validation Query")
            .type(ProviderConfigProperty.STRING_TYPE)
            .helpText("SQL query used to validate a connection")
            .defaultValue("select 1")
            .add()
          .build();   
          
    }

    @Override
    public CustomUserStorageProvider create(KeycloakSession ksession, ComponentModel model) {
        log.log(Level.INFO, "[I63] creating new CustomUserStorageProvider");
        return new CustomUserStorageProvider(ksession, model);
    }

    @Override
    public String getId() {
        log.log(Level.INFO, "[I69] getId()");
        return "custom-user-provider";
    }

    
    // Configuration support methods
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config) throws ComponentValidationException {
        log.log(Level.INFO, "Driver=" + config.get(CONFIG_KEY_JDBC_DRIVER));
        log.log(Level.INFO, "URL=" + config.get(CONFIG_KEY_JDBC_URL));
        log.log(Level.INFO, "user=" + config.get(CONFIG_KEY_DB_USERNAME));
        log.log(Level.INFO, "pass=" + config.get(CONFIG_KEY_DB_PASSWORD));
        log.log(Level.INFO, "Query=" + config.get(CONFIG_KEY_VALIDATION_QUERY));
    	
    	
       try (Connection c = DbUtil.getConnection(config)) {
           log.log(Level.INFO, "[I84] Testing connection..." );
           c.createStatement().execute(config.get(CONFIG_KEY_VALIDATION_QUERY));
           log.log(Level.INFO, "[I92] Connection OK !" );
       }
       catch(Exception ex) {
           log.log(Level.WARNING, "[W94] Unable to validate connection: ex= " + ex.getMessage() + "");
           throw new ComponentValidationException("Unable to validate database connection",ex);
       }
    }

    @Override
    public void onUpdate(KeycloakSession session, RealmModel realm, ComponentModel oldModel, ComponentModel newModel) {
        log.log(Level.INFO, "[I94] onUpdate()" );
    }

    @Override
    public void onCreate(KeycloakSession session, RealmModel realm, ComponentModel model) {
        log.log(Level.INFO, "[I99] onCreate()" );
    }
}