package com.pc.demo.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pc.demo.model.common.Result;
import com.pc.demo.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> authorityList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String authorities = StringUtils.join(authorityList, ",");
        String token = JwtTokenUtil.createToken(userDetails.getUsername(), authorities);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", userDetails);
        result.put("loginName", userDetails.getUsername());
        out(response, Result.builder().code(200).msg("成功").data(result).build());
    }

    private void out(HttpServletResponse response, Result result) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = null;
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            writer = response.getWriter();
            mapper.writeValue(writer, result);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
