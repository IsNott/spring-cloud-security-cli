package org.nott.cli.security.jwt;


import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.nott.cli.common.utils.SpringContextUtils;
import org.nott.cli.common.handler.HttpHandler;
import org.nott.cli.common.model.Result;
import org.nott.cli.security.config.JwtConfig;
import org.nott.cli.security.manager.AuthenticationTokenImpl;
import org.nott.cli.security.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * token管理 1. 登陆返回token 2. 刷新token 3. 清除用户过去token 4. 校验token
 *
 * @date 2020/7/2
 */
@Component
public class RecruitTokenService {

    private HttpHandler httpHandler;

    private JwtConfig jwtConfig;

    private String secret;

    private static final Logger logger = LoggerFactory.getLogger(RecruitTokenService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public HttpHandler getHttpHandler() {
        if (this.httpHandler == null) {
            ApplicationContext context = SpringContextUtils.applicationContext;
            HttpHandler bean = context.getBean("httpHandler", HttpHandler.class);
            this.httpHandler = bean;
        }
        return httpHandler;
    }

    public RecruitTokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.secret = Sha512DigestUtils.shaHex(getJwtConfig().getSecret());
    }


    @PostConstruct
    public JwtConfig getJwtConfig() {
        if (this.jwtConfig == null) {
            ApplicationContext context = SpringContextUtils.applicationContext;
            JwtConfig bean = context.getBean("jwtConfig", JwtConfig.class);
            this.jwtConfig = bean;
        }
        return this.jwtConfig;
    }

    public void addAuthentication(HttpServletResponse response, AuthenticationTokenImpl auth) {
        // We generate a token now.
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", auth.getPrincipal());
        claims.put("hash", auth.getHash());
        String JWT = Jwts.builder()
                .setSubject(auth.getPrincipal().toString())
                .setClaims(claims)
                .setExpiration(DateUtil.date(System.currentTimeMillis() + jwtConfig.getExpireTime()))
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();

        String token = jwtConfig.getTokenPrefix() + " " + JWT;
        response.addHeader(jwtConfig.getHeader(), token);

        httpHandler.printServerResponseToWeb(Result.success(token));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        Authentication authentication = null;
        String token = request.getHeader(jwtConfig.getHeader());
        if (token == null) {
            return null;
        }
        //remove "Bearer" text
        token = token.replace(jwtConfig.getTokenPrefix(), "").trim();

        authentication = getAuthentication(token, authentication);
        return authentication;
    }

    private Authentication getAuthentication(String token, Authentication authentication) {
        //Validating the token
        if (StringUtils.isNotEmpty(token)) {
            // parsing the token.`
            Claims claims = null;
            claims = getClaims(claims, token);

            //Valid token and now checking to see if the token is actally expired or alive by quering in redis.
            if (claims != null && claims.containsKey("username")) {
                String username = claims.get("username").toString();
                String hash = claims.get("hash").toString();
                LinkedHashMap<String, Object> obj = (LinkedHashMap) redisTemplate.opsForValue().get(String.format("%s:%s", username, hash));
                SysUser user = null;
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String valueAsString = mapper.writeValueAsString(obj);
                    user = mapper.readValue(valueAsString, SysUser.class);
                } catch (JsonProcessingException e) {
                    logger.error("ObjectMapper read error :{}", e.getMessage(), e);
                }
                if (user != null) {
                    AuthenticationTokenImpl auth = new AuthenticationTokenImpl(user.getUsername(), Collections.emptyList());
                    auth.setDetails(user);
                    auth.authenticate();
                    authentication = auth;
                } else {
                    authentication = new UsernamePasswordAuthenticationToken(null, null);
                }
            }
        }
        return authentication;
    }

    private Claims getClaims(Claims claims, String token) {
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();

        } catch (Exception e) {
            logger.error("parse token error :{}", e.getMessage(), e);
        }
        return claims;
    }

    public boolean removeAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeader());
        boolean result = false;
        if (token != null) {
            token = token.replace(jwtConfig.getTokenPrefix(), "").trim();
            Authentication authentication = getAuthentication(token, null);
            if (authentication != null && authentication instanceof AuthenticationTokenImpl) {
                AuthenticationTokenImpl authenticationToken = (AuthenticationTokenImpl) authentication;
                String username = (String) authenticationToken.getPrincipal();
                result = redisTemplate.delete(String.format("%s:%s",username,authenticationToken.getHash()));
            }
        }
        return result;
    }

}

