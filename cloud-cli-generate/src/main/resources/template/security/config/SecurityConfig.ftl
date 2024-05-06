package ${parent.groupId}.${parent.childLastPackage}.security.config;

import ${parent.groupId}.${parent.childLastPackage}.security.adapter.AuthConfigAdapter;
import ${parent.groupId}.${parent.childLastPackage}.security.adapter.ResourceServerAdapter;
import ${parent.groupId}.${parent.childLastPackage}.security.jwt.RecruitTokenService;
import ${parent.groupId}.${parent.childLastPackage}.security.manager.AuthenticationProviderImpl;
import ${parent.groupId}.${parent.childLastPackage}.security.jwt.JWTAuthenticationFilter;
import ${parent.groupId}.${parent.childLastPackage}.security.jwt.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private RecruitTokenService tokenService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    private AuthConfigAdapter authConfigAdapter;

    public AuthConfigAdapter getAuthConfigAdapter() {
        return new ResourceServerAdapter();
    }

    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList((AuthenticationProviderImpl) new AuthenticationProviderImpl(redisTemplate)));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 'In Spring Security 5.7.0-M2 we deprecated the WebSecurityConfigurerAdapter,
     *   as we encourage users to move towards a component-based security configuration.'
     * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.headers().cacheControl();
        String[] excludPaths = getAuthConfigAdapter().excludePathPatterns().toArray(new String[0]);
        return http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .requestMatchers(excludPaths)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable()
                .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager(), tokenService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .build()
        ;
    }

}
