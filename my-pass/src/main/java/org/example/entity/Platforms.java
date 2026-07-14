package org.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@TableName("platforms")
public class Platforms {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("category_id")
    private Long categoryId;

    @TableField("name")
    private String name;

    @TableField("url")
    private String url;

    @TableField("sort_order")
    private Integer sortOrder;

    // 创建时间：插入时忽略（交给数据库 DEFAULT），更新时默认不处理
    @TableField(value = "created_at", insertStrategy = FieldStrategy.NEVER)
    private LocalDateTime createdAt;

    // 更新时间：插入时忽略（交给数据库 DEFAULT），更新时也忽略（交给数据库 ON UPDATE）
    @TableField(value = "updated_at", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updatedAt;
}