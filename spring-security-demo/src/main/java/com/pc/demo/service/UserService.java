package com.pc.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.demo.model.entity.User;

import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author PC
 * @since 2021-09-10
 */
public interface UserService extends IService<User> {

    Optional<User> getByUniqueKey(String username);
}
