package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.exception.MemberException.NoAuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        Long memberId = (Long) webRequest.getAttribute("memberId", WebRequest.SCOPE_SESSION);

        if (memberId == null) {
            throw new NoAuthorizationException();
        }
        return memberId;
    }
}
