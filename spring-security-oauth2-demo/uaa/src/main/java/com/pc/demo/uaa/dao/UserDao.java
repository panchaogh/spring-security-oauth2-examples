package com.pc.demo.uaa.dao;

import com.pc.demo.uaa.model.entity.User;
import com.pc.demo.uaa.model.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        String sql = "select id,username,password,fullname from t_user where username = ?";
        List<User> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        if (Objects.isNull(list) || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Long> findRolesByUserId(String userId) {
        String sql = "select * from t_user_role where user_id=?";
        List<UserRole> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(UserRole.class));
        if (Objects.isNull(list) || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    public List<Map<String, Object>> findPermissionRole() {
        String sql = "select a.url as url,b.role_id as roleId from t_permission a,t_role_permission b where a.id=b.permission_id";
        return jdbcTemplate.queryForList(sql);
    }
}
