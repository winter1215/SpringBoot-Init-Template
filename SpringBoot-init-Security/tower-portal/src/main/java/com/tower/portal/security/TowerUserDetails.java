package com.tower.portal.security;

import com.tower.common.enums.AuthStatus;
import com.tower.common.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author CYY
 * @date 2022年11月24日 下午10:30
 * @description
 */
@AllArgsConstructor
@Getter
@Setter
public class TowerUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否被封禁
     */
    @Override
    public boolean isAccountNonLocked() {
        return AuthStatus.USER_BANNED.getCode() != user.getStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
