package org.example.service;


import org.example.entity.Users;

/**
 *
 *
 * <p></p>
 *
 * @author WenLong
 * @version 1.0.0
 * @date 2026/7/14 09:59
 * @see UsersService
 */
public interface UsersService {
    Users findByUserName(String userName);
    Users findById(Long id);
    void createUser(String userName, String hashed);
}