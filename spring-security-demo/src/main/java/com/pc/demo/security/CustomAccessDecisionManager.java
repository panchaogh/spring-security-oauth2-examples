package com.pc.demo.security;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        authentication可获取当前用户拥有的角色集合
//        configAttributes 路径拦截处理中查询到的，能访问当前路径的角色集合
        //当前用户拥有的角色集合
        List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //访问路径所需要的角色集合
        List<String> configRoleCodes = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        for (String userRole : userRoles) {
            if (configRoleCodes.contains(userRole)) {
                return;
            }
        }
        throw new AccessDeniedException("permission denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
