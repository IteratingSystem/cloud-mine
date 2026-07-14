package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Users;
@Mapper
public interface UsersMapper {
    Users findByUsername(@Param("username") String username);

    Users selectById(Long id);

    void insert(Users user);
}
