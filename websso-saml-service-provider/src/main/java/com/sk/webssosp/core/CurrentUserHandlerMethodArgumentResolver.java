package com.sk.webssosp.core;

import java.security.Principal;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sk.webssosp.model.AppUser;
import com.sk.webssosp.stereotype.CurrentUser;

/**
 * @author Sandeep Kumar
 */
@Component public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CurrentUser.class) != null
            && methodParameter.getParameterType().equals(AppUser.class);
    }
    
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
        throws Exception {
        if (this.supportsParameter(methodParameter)) {
            Principal principal = webRequest.getUserPrincipal();
            return (null != principal)?(AppUser) ((Authentication) principal).getPrincipal():null;
        } else {
            return WebArgumentResolver.UNRESOLVED;
        }
    }
}
