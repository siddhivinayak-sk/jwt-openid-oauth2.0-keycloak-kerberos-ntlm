package com.sk.webssosp.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.sk.webssosp.dto.HttpCall;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application")
public class AppConfig {
    
    private Map<String, String> url;
    private List<String> domains;
    private Map<String, String> idpLebels;
	private List<HttpCall> redirectauth;
	private List<Resource> idps;
	private Boolean directcall;

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public Map<String, String> getIdpLebels() {
		return idpLebels;
	}

	public void setIdpLebels(Map<String, String> idpLebels) {
		this.idpLebels = idpLebels;
	}

	public List<HttpCall> getRedirectauth() {
		return redirectauth;
	}

	public void setRedirectauth(List<HttpCall> redirectauth) {
		this.redirectauth = redirectauth;
	}

	public List<Resource> getIdps() {
		return idps;
	}

	public void setIdps(List<Resource> idps) {
		this.idps = idps;
	}

	public Boolean getDirectcall() {
		return directcall;
	}

	public void setDirectcall(Boolean directcall) {
		this.directcall = directcall;
	}
}
