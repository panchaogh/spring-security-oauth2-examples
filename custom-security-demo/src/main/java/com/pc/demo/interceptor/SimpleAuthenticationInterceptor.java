package com.pc.demo.interceptor;

import com.pc.demo.model.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //读取会话信息
        Object object = request.getSession().getAttribute(UserDTO.SESSION_USER_KEY);
        if (Objects.isNull(object)) {
            writeContent(response, "请登录");
            return false;
        }
        UserDTO user = (UserDTO) object;
        //请求的url
        String requestURI = request.getRequestURI();
        if (user.getAuthorities().contains("p1") && requestURI.contains("/r1")) {
            return true;
        }
        if (user.getAuthorities().contains("p2") && requestURI.contains("/r2")) {
            return true;
        }
        writeContent(response, "权限不足，拒绝访问");
        return false;
    }

    //响应输出
    private void writeContent(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(msg);
        writer.close();
    }
}
