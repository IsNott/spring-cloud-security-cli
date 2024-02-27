package org.nott.cli.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nott.cli.common.utils.CommonUtils;
import org.nott.cli.common.xss.XssWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class XssFilter implements Filter{

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;


        logger.info("Request URI ====>:{}",req.getRequestURI());
        logger.info("Request RequestBody : {}", CommonUtils.getHttpRequestBody(req));
        // do xss filter
        chain.doFilter(new XssWrapper(req), resp);
    }

    @Override
    public void destroy() {

    }
}
