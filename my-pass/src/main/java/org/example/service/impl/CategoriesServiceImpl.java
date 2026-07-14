package org.example.service.impl;

import org.example.entity.Categories;
import org.example.entity.dto.CategorySort;
import org.example.exception.BusinessException;
import org.example.mapper.CategoriesMapper;
import org.example.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public List<Categories> listByUserId(Long userId) {
        return categoriesMapper.selectByUserIdOrderBySort(userId);
    }

    @Override
    public Categories create(Long userId, String name) {
        // 检查是否已存在同名分类（可选，由数据库唯一约束保证）
        if (categoriesMapper.hasName(userId, name)) {
            return null;
        }
        // 计算新排序值：当前用户最大 sort_order + 1
        Integer maxSort = categoriesMapper.selectMaxSortOrderByUserId(userId);
        int sortOrder = (maxSort == null) ? 0 : maxSort + 1;

        Categories category = new Categories();
        category.setUserId(userId);
        category.setName(name);
        category.setSortOrder(sortOrder);

        categoriesMapper.insert(category);
        return category;
    }

    @Override
    public boolean delete(Long userId, Long categoryId) {
        Categories category = categoriesMapper.selectById(categoryId);
        if (category == null || !category.getUserId().equals(userId)) {
            return false;
        }
        int i = categoriesMapper.deleteById(categoryId);
        return i > 0;
    }

    @Override
    public void reorder(Long userId, List<Long> categoryIds) {
        // 1. 验证所有分类属于该用户
        List<Categories> existing = categoriesMapper.selectBatchIds(categoryIds);
        if (existing.size() != categoryIds.size()) {
            throw new BusinessException("部分分类不存在");
        }
        for (Categories cat : existing) {
            if (!cat.getUserId().equals(userId)) {
                throw new BusinessException("无权操作其他用户的分类");
            }
        }

        // 2. 构造更新数据
        List<CategorySort> sortList = new ArrayList<>();
        for (int i = 0; i < categoryIds.size(); i++) {
            CategorySort sort = new CategorySort();
            sort.setId(categoryIds.get(i));
            sort.setSortOrder(i); // 从0开始递增
            sortList.add(sort);
        }

        // 3. 批量更新
        categoriesMapper.batchUpdateSortOrder(sortList);
    }
}