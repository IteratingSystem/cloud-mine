package org.example.service;

import org.example.entity.Categories;

import java.util.List;

public interface CategoriesService {

    /**
     * 查询用户所有分类，按 sort_order 升序
     */
    List<Categories> listByUserId(Long userId);

    /**
     * 新增分类（自动计算排序值）
     */
    Categories create(Long userId, String name);

    /**
     * 删除分类（校验归属）
     */
    boolean delete(Long userId, Long categoryId);

    /**
     * 重新排序（批量更新 sort_order）
     */
    void reorder(Long userId, List<Long> categoryIds);
}