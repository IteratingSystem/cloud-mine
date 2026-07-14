package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 密码分类表实体
 *
 * @author WenLong
 * @date 2026-07-14
 */
@Getter
@Setter
@TableName("categories")
public class Categories {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序权重
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 创建时间（数据库自动生成）
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
}