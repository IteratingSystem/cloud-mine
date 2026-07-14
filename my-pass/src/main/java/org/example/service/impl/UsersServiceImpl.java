package org.example.service.impl;

import org.example.mapper.UsersMapper;
import org.example.entity.Users;
import org.example.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service   // 必须加
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users findByUserName(String userName) {
        return usersMapper.findByUserName(userName);
    }

    @Override
    public Users findById(Long id) {
        return usersMapper.selectById(id);   // MyBatis-Plus 内置方法
    }

    @Override
    public void createUser(String userName, String passwordHash) {
        Users user = new Users();
        user.setUserName(userName);
        user.setPasswordHash(passwordHash);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        usersMapper.insert(user);   // MyBatis-Plus 内置方法
    }
}