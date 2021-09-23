package com.sk.webssosp.core;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.saml2.core.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

import com.sk.webssosp.model.AppUser;

/**
 * @author Sandeep Kumar
 */
@Service public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);
    
    @Value("${application.defaultRelayState:}")
    private String defaultRelayState;
    
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        
        String relayState = credential.getRelayState();
        if(null == relayState) {
        	relayState = defaultRelayState;
        }
        
        String userID = credential.getNameID().getValue();
        LOG.info("Logged in user: {}", userID);
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);
        
        AppUser appUser = new AppUser(userID, relayState, true, true, true, true, authorities);
        List<Attribute> attributes = credential.getAttributes();
        if(null != attributes) {
        	attributes.stream().forEach(attribute -> appUser.addAttribute(attribute.getName(), credential.getAttributeAsString(attribute.getName())));
        }
        appUser.addAttribute("RemoteEntityID", credential.getRemoteEntityID());
        appUser.addAttribute("LocalEntityID", credential.getLocalEntityID());
        
        return appUser;
    }
    
}
