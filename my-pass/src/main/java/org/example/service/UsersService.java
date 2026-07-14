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
    Users findByUsername(String username);
    Users findById(Long id);
    void createUser(String username, String hashed);
}