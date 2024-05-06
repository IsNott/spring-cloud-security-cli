package org.nott.cli.security.manager;

import org.apache.commons.lang3.StringUtils;
import org.nott.cli.common.utils.SpringContextUtils;
import org.nott.cli.security.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AuthenticationProviderImpl implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService;

    private RedisTemplate<String, Object> redisTemplate;

    public PasswordEncoder getPasswordEncoder() {
        if(this.passwordEncoder == null){
            this.passwordEncoder = SpringContextUtils.getBean("passwordEncoder", PasswordEncoder.class);
        }
        return passwordEncoder;
    }

    public UserDetailsService getUserDetailsService() {
        if(this.userDetailsService == null){
            ApplicationContext context = SpringContextUtils.applicationContext;
            this.userDetailsService = context.getBean("usersServiceImpl", UserDetailsService.class);
        }
        return userDetailsService;
    }

    public AuthenticationProviderImpl(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        if (StringUtils.isEmpty(username)) {
            logger.error("Username {} not found.",username);
            throw new BadCredentialsException("Username not found.");
        }
        if (password.length() < 5) {
            throw new BadCredentialsException("Wrong password.");
        }

        Objects.requireNonNull(getUserDetailsService(),"UserDetailService not found");
        UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);
        String encodedPass = getPasswordEncoder().encode(password);

        //Right now just authenticate on the basis of the user=pass
        if (!passwordEncoder.matches(userDetails.getPassword(),encodedPass)) {
            SysUser u = new SysUser();
            u.setUsername(username);
            u.setStoredTime(new Date());
            AuthenticationTokenImpl auth = new AuthenticationTokenImpl(u.getUsername(), Collections.emptyList());
            auth.setAuthenticated(true);
            auth.setDetails(u);
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(String.format("%s:%s", u.getUsername().toLowerCase(), auth.getHash()), u, 3600L, TimeUnit.SECONDS);
            return auth;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
