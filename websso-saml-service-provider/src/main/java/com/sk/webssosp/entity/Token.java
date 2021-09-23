package com.sk.webssosp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TOKEN_REPO") 
public class Token implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id 
    @Column(name = "ID_TOKEN") 
    private String idToken;
    
    @Column(name = "USER_NAME", length = 200)
    private String username;
    
    @Column(name = "DOMAIN", length = 100) 
    private String domain;
    
    @Column(name = "ACCESS_TOKEN", length = 2000) 
    private String accessToken;
    
    @Column(name = "REFRESH_TOKEN", length = 2000) 
    private String refreshToken;
    
    @Column(name = "CREATED_DATE") 
    private Date createdDate;
    
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

	public Date getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
