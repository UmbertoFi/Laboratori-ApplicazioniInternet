package com.example.demo;

import com.example.demo.Entity.Utente;
import com.example.demo.Entity.UtenteRuolo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MyUserPrincipal implements UserDetails {
    private Utente user;

    public MyUserPrincipal(Utente user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("user"));
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getExpiredAccount();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getExpiredCredential();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
