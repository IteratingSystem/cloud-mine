package org.example.service;

import org.example.entity.Platforms;

import java.util.List;

public interface PlatformsService {

    /**
     * 获取平台列表（按用户，可选分类过滤）
     */
    List<Platforms> listByUserIdAndCategory(Long userId, Long categoryId);

    /**
     * 新增平台
     */
    Platforms create(Long userId, String name, String url, Long categoryId);

    /**
     * 删除平台
     */
    void delete(Long userId, Long platformId);

    /**
     * 修改平台名称
     */
    void updateName(Long userId, Long platformId, String newName);

    /**
     * 重新排序（全局排序）
     */
    void reorder(Long userId, List<Long> platformIds);
}