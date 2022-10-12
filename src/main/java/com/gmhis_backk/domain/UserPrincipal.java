package com.gmhis_backk.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;

public class UserPrincipal implements UserDetails {
  
	private static final long serialVersionUID = 1L;
	
	private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	
    	if(StringUtils.isNoneBlank(user.getAuthorities())) {
    		List<String> userAutorities = Arrays.asList(user.getAuthorities().split(","));
        	userAutorities.stream()
            .map(p -> new SimpleGrantedAuthority(p))
            .forEach(authorities::add);
    	}
    	
    	return authorities;
    }
    
    public Long getId() {
        return this.user.getId();
    }


    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
}
