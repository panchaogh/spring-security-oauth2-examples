package com.pc.demo.security;

import com.pc.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.*;
import java.util.stream.Collectors;

public class CustomFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private UserDao userDao;
    private final Map<String, Collection<ConfigAttribute>> temp = new HashMap<>();

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
            List<Map<String, Object>> list = userDao.findPermissionRole();
            Map<String, List<Map<String, Object>>> urlRoles = list.stream().collect(Collectors.groupingBy(map -> (String) map.get("url")));
            for (Map.Entry<String, List<Map<String, Object>>> entry : urlRoles.entrySet()) {
                temp.put(entry.getKey(), entry.getValue().stream().map(map -> new SecurityConfig((String) map.get("roleId"))).collect(Collectors.toList()));
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
        return Arrays.asList("/login", "/error","/css/**", "/fonts/**", "/js/**", "/scss/**", "/img/**");
    }

    /**
     * @return 默认拒绝访问配置
     */
    private List<ConfigAttribute> deniedRequest() {
        return Collections.singletonList(new SecurityConfig("ROLE_DENIED"));
    }
}
