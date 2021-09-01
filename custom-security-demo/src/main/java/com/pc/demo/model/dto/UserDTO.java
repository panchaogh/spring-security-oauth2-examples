package com.pc.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    public static final String SESSION_USER_KEY = "_user";

    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;

    private Set<String> authorities;
}
