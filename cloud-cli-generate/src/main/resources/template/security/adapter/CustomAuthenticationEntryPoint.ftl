package ${parent.groupId}.${parent.childLastPackage}.security.adapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import ${parent.groupId}.${parent.childLastPackage}.common.utils.SpringContextUtils;
import ${parent.groupId}.${parent.childLastPackage}.common.enums.ResponseEnum;
import ${parent.groupId}.${parent.childLastPackage}.common.handler.HttpHandler;
import ${parent.groupId}.${parent.childLastPackage}.common.model.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ${parent.author}
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
