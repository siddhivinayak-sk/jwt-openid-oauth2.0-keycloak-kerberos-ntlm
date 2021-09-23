package com.sk.config;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class CustomPostZuulFilter extends ZuulFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object run() {
		final RequestContext ctx = RequestContext.getCurrentContext();
		logger.info("in zuul filter " + ctx.getRequest().getRequestURI());

		final String requestURI = ctx.getRequest().getRequestURI();

		try {
			Map<String, List<String>> params = ctx.getRequestQueryParams();

			if (requestURI.contains("auth/redirect")) {
				final Cookie cookie = new Cookie("code", params.get("code").get(0));
				cookie.setHttpOnly(true);
				cookie.setPath(ctx.getRequest().getContextPath() + "/auth/token");
				cookie.setMaxAge(2592000); // 30 days

				ctx.getResponse().addCookie(cookie);

			} else if (requestURI.contains("auth/token") || requestURI.contains("auth/refresh")) {
				if (requestURI.contains("auth/refresh/revoke")) {

					final Cookie cookie = new Cookie("refreshToken", "");
					cookie.setMaxAge(0);
					ctx.getResponse().addCookie(cookie);
				} else {
					final InputStream is = ctx.getResponseDataStream();
					String responseBody = IOUtils.toString(is, "UTF-8");
					if (responseBody.contains("refresh_token")) {
						final Map<String, Object> responseMap = mapper.readValue(responseBody,
								new TypeReference<Map<String, Object>>() {
								});
						final String refreshToken = responseMap.get("refresh_token").toString();
						responseMap.remove("refresh_token");
						responseBody = mapper.writeValueAsString(responseMap);

						final Cookie cookie = new Cookie("refreshToken", refreshToken);
						cookie.setHttpOnly(true);
						cookie.setPath(ctx.getRequest().getContextPath() + "/auth/refresh");
						cookie.setMaxAge(2592000); // 30 days

						ctx.getResponse().addCookie(cookie);
					}
					ctx.setResponseBody(responseBody);
				}

			}
		} catch (Exception e) {
			logger.error("Error occured in zuul post filter", e);
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		boolean shouldfilter = false;
		final RequestContext ctx = RequestContext.getCurrentContext();
		String URI = ctx.getRequest().getRequestURI();

		if (URI.contains("auth/redirect") || URI.contains("auth/token") || URI.contains("auth/refresh")) {
			shouldfilter = true;
		}

		return shouldfilter;
	}

	@Override
	public int filterOrder() {
		return 10;
	}

	@Override
	public String filterType() {
		return "post";
	}

}
