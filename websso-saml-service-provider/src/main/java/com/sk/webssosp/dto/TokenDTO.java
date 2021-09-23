package com.sk.webssosp.dto;

public class TokenDTO {
    private String idToken;
    private String username;
    private String domain;
    private String accessToken;
    private String refreshToken;
    private boolean needRefreshToken;
    
    
    
    
    public TokenDTO() {
		super();
	}

	public String getIdToken() {
        return idToken;
    }
    
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

	public boolean isNeedRefreshToken() {
		return needRefreshToken;
	}

	public void setNeedRefreshToken(boolean needRefreshToken) {
		this.needRefreshToken = needRefreshToken;
	}
        
}
