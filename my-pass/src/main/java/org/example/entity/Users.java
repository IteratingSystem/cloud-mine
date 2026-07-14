package org.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
/**
 * User表
 *
 * <p></p>
 *
 * @author WenLong
 * @version 1.0.0
 * @date 2026/7/14 09:41
 * @see Users
 */


@TableName("users")
@Getter
@Setter
public class Users {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password_hash")
    private String passwordHash;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}