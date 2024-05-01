package org.nott.cli.security.adapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.nott.cli.common.utils.SpringContextUtils;
import org.nott.cli.common.enums.ResponseEnum;
import org.nott.cli.common.handler.HttpHandler;
import org.nott.cli.common.model.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Nott
 * @date 2024-4-3
 */
@Component(value = "authenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpHandler httpHandler = (HttpHandler) SpringContextUtils.getBean("httpHandler");
        httpHandler.printServerResponseToWeb(StringUtils.isNotEmpty(authException.getMessage()) ? Result.fail(403,authException.getMessage()) : Result.fail(ResponseEnum.UNAUTHORIZED));
    }
}
