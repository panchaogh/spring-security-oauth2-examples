package com.pc.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.demo.mapper.UserMapper;
import com.pc.demo.model.entity.User;
import com.pc.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author PC
 * @since 2021-09-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Optional<User> getByUniqueKey(String username) {
        User user = null;
        if (StringUtils.isNotBlank(username)) {
            LambdaQueryWrapper<User> queryWrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, username);
            user = getBaseMapper().selectOne(queryWrapper);
        }
        return Optional.ofNullable(user);
    }
}
