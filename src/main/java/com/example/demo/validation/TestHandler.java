package com.example.demo.validation;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Component
public class TestHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String langParam = request.getParameter("langParam");
        String langHeader = request.getHeader("accept-language");
        if(!StringUtils.hasText(langHeader) && StringUtils.hasText(langParam)) {
            LocaleContextHolder.setLocale(new Locale(langParam));
        }
        return true;
    }
}
