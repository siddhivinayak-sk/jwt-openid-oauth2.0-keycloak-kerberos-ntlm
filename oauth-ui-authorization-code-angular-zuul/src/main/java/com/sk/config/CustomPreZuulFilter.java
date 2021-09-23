package com.sk.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class CustomPreZuulFilter extends ZuulFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static String REDIRECT_URL = "http://localhost:8089/auth/redirect/";
	private final static String CLIENT_ID = "newClient";
	private final static String CLIENT_SECRET = "newClientSecret";

	@Override
	public Object run() {
		final RequestContext ctx = RequestContext.getCurrentContext();
		logger.info("in zuul filter URI:" + ctx.getRequest().getRequestURI());

		final HttpServletRequest req = ctx.getRequest();
		String requestURI = req.getRequestURI();

		if (requestURI.contains("auth/code")) {
			Map<String, List<String>> params = ctx.getRequestQueryParams();
			if (params == null) {
				params = Maps.newHashMap();
			}
			params.put("response_type", Lists.newArrayList(new String[] { "code" }));
			params.put("scope", Lists.newArrayList(new String[] { "read" }));
			params.put("client_id", Lists.newArrayList(new String[] { CLIENT_ID }));
			params.put("redirect_uri", Lists.newArrayList(new String[] { REDIRECT_URL }));

			ctx.setRequestQueryParams(params);
		} else if (requestURI.contains("auth/token") || requestURI.contains("auth/refresh")) {
			try {
				byte[] bytes;
				if (requestURI.contains("auth/refresh/revoke")) {
					String cookieValue = extractCookie(req, "refreshToken");
					String formParams = createFormData(requestURI, cookieValue);
					bytes = formParams.getBytes("UTF-8");
				} else {
					String cookieValue = requestURI.contains("token") ? extractCookie(req, "code")
							: extractCookie(req, "refreshToken");
					String formParams = createFormData(requestURI, cookieValue);
					bytes = formParams.getBytes("UTF-8");
				}
				ctx.setRequest(new CustomHttpServletRequest(req, bytes));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	private String extractCookie(HttpServletRequest req, String name) {
		final Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(name)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	private String createFormData(String requestURI, String cookieValue) {
		String formData = "";
		if (requestURI.contains("token")) {
			formData = String.format("grant_type=%s&client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
					"authorization_code", CLIENT_ID, CLIENT_SECRET, REDIRECT_URL, cookieValue);
		} else if (requestURI.contains("refresh")) {
			if (requestURI.contains("revoke")) {
				formData = String.format("client_id=%s&client_secret=%s&refresh_token=%s", CLIENT_ID, CLIENT_SECRET,
						cookieValue);
			} else {
				formData = String.format("grant_type=%s&client_id=%s&client_secret=%s&refresh_token=%s",
						"refresh_token", CLIENT_ID, CLIENT_SECRET, cookieValue);
			}

		}
		return formData;
	}

	@Override
	public boolean shouldFilter() {
		boolean shouldfilter = false;
		final RequestContext ctx = RequestContext.getCurrentContext();
		String URI = ctx.getRequest().getRequestURI();

		if (URI.contains("auth/code") || URI.contains("auth/token") || URI.contains("auth/refresh")) {
			shouldfilter = true;
		}

		return shouldfilter;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
