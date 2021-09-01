package com.pc.demo.security;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class CustomPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
    private final CustomAccessDecisionManager customAccessDecisionManager;
    private final CustomFilterSecurityMetadataSource customFilterSecurityMetadataSource;

    public CustomPostProcessor(CustomAccessDecisionManager customAccessDecisionManager,
                               CustomFilterSecurityMetadataSource customFilterSecurityMetadataSource) {
        this.customAccessDecisionManager = customAccessDecisionManager;
        this.customFilterSecurityMetadataSource = customFilterSecurityMetadataSource;
    }

    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
        object.setAccessDecisionManager(customAccessDecisionManager); //权限决策处理类
        object.setSecurityMetadataSource(customFilterSecurityMetadataSource); //路径（资源）拦截处理
        return object;
    }
}
