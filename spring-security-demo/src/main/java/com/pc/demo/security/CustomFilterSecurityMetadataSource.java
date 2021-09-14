package com.pc.demo.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pc.demo.model.entity.Permission;
import com.pc.demo.model.entity.RolePermission;
import com.pc.demo.service.PermissionService;
import com.pc.demo.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.*;
import java.util.stream.Collectors;

public class CustomFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final Map<String, Collection<ConfigAttribute>> temp = new HashMap<>();
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object; //当前请求对象
        if (isMatcherAllowedRequest(filterInvocation)) return null;
        Collection<ConfigAttribute> configAttributes = getMatcherConfigAttribute(filterInvocation.getRequestUrl());
        return configAttributes.isEmpty() ? deniedRequest() : configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 获取当前路径所需要的角色
     * @param url 当前路径
     * @return 所需角色集合
     */
    private Collection<ConfigAttribute> getMatcherConfigAttribute(String url) {
        if (temp.isEmpty()) {
            List<Permission> permissions = permissionService.list();
            List<Long> permissionIds = permissions.stream().map(Permission::getId).collect(Collectors.toList());
            LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getPermissionId, permissionIds);
            List<RolePermission> rolePermissions = rolePermissionService.list(rolePermissionLambdaQueryWrapper);
            Map<Long, List<RolePermission>> rolePermissionMap = rolePermissions.stream().collect(Collectors.groupingBy(RolePermission::getPermissionId));

            for (Permission permission : permissions) {
                temp.put(permission.getUrl(), rolePermissionMap.get(permission.getId()).stream().map(rolePermission -> new SecurityConfig("ROLE_" + rolePermission.getRoleId())).collect(Collectors.toList()));
            }
        }
        Collection<ConfigAttribute> configAttributes = temp.get(url);
        return Objects.isNull(configAttributes) ? new ArrayList<>() : configAttributes;
    }

    /**
     * 判断当前请求是否在允许请求的范围内
     */
    private boolean isMatcherAllowedRequest(FilterInvocation filterInvocation) {
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
            .filter(requestMatcher -> requestMatcher.matches(filterInvocation.getHttpRequest()))
            .toArray().length > 0;
    }

    /**
     * @return 定义允许请求的列表
     */
    private List<String> allowedRequest() {
        return Arrays.asList("/login", "/error", "/css/**", "/fonts/**", "/js/**", "/scss/**", "/img/**");
    }

    /**
     * @return 默认拒绝访问配置
     */
    private List<ConfigAttribute> deniedRequest() {
        return Collections.singletonList(new SecurityConfig("ROLE_DENIED"));
    }
}
