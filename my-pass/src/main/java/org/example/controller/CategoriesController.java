package org.example.controller;

import org.apache.ibatis.annotations.Param;
import org.example.entity.Categories;
import org.example.result.R;
import org.example.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    /**
     * 查询当前用户的所有分类（按 sort_order 升序）
     * GET /categories
     * 请求头：X-User-Id
     */
    @GetMapping("/getList")
    public R<List<Categories>> list(@RequestHeader("X-User-Id") Long userId) {
        List<Categories> list = categoriesService.listByUserId(userId);
        return R.success(list);
    }

    /**
     * 新增分类
     * POST /categories
     * 请求体：{"name":"社交"}
     * 请求头：X-User-Id
     */
    @PostMapping("/add")
    public R<Categories> add(@RequestBody Map<String, String> params,
                             @RequestHeader("X-User-Id") Long userId) {
        String name = params.get("name");
        if (name == null || name.trim().isEmpty()) {
            return R.error("分类名称不能为空");
        }
        Categories category = categoriesService.create(userId, name.trim());
        if (category == null) {
            return R.error("添加失败,确认是否输入正确(不重复)的分类名!");
        }
        return R.success(category);
    }

    /**
     * 删除分类
     * DELETE
     * 请求头：X-User-Id
     */
    @DeleteMapping("/delete")
    public R<String> delete(@Param("id") Long id,
                          @RequestHeader("X-User-Id") Long userId) {
        boolean delete = categoriesService.delete(userId, id);
        if (!delete) {
            return R.error("删除失败,没有被删除的分类");
        }

        return R.success("删除成功");
    }

    /**
     * 重新排序
     * PUT /categories/order
     * 请求体：[3, 1, 2]  (按顺序排列的分类ID列表)
     * 请求头：X-User-Id
     * 说明：将列表中的分类ID，按顺序设置 sort_order 为 0,1,2,...
     */
    @PutMapping("/order")
    public R<Void> reorder(@RequestBody List<Long> categoryIds,
                           @RequestHeader("X-User-Id") Long userId) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return R.error("分类列表不能为空");
        }
        categoriesService.reorder(userId, categoryIds);
        return R.success(null);
    }
}