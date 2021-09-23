package com.sk.webssosp.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AppUser extends User {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> additionalAttributes;
	
	public AppUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		additionalAttributes = new HashMap<>();
	}
	
	
	public AppUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		additionalAttributes = new HashMap<>();
	}

	public Map<String, String> getAdditionalAttributes() {
		return additionalAttributes;
	}


	public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}
	
	public String addAttribute(String attributeName, String attributeValue) {
		return additionalAttributes.put(attributeName, attributeValue);
	}

	public String getAttribute(String attributeName) {
		return additionalAttributes.get(attributeName);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
