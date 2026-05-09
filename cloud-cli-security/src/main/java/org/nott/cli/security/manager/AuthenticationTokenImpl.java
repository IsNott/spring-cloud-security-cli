package org.nott.cli.security.manager;

import lombok.Setter;
import lombok.ToString;
import org.nott.cli.security.model.Users;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.DigestUtils;

import java.util.Collection;

@ToString(callSuper = true)
public class AuthenticationTokenImpl extends AbstractAuthenticationToken {

    @Setter
    private String username;

    public AuthenticationTokenImpl(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = principal;
    }

    public void authenticate() {
        if (getDetails() != null && getDetails() instanceof Users && !((Users) getDetails()).hasExpired()) {
            setAuthenticated(true);
        } else {
            setAuthenticated(false);
        }
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return username != null ? username.toString() : "";
    }

    public String getHash() {
        return DigestUtils.md5DigestAsHex(String.format("%s", username).getBytes());
    }
}
