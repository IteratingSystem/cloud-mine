package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Users;
@Mapper
public interface UsersMapper {
    Users findByUserName(@Param("userName") String username);

    Users selectById(Long id);

    void insert(Users user);
}
