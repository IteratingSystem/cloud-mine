package org.example.controller;

import org.example.entity.Platforms;
import org.example.result.R;
import org.example.service.PlatformsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/platforms")
public class PlatformsController {

    @Autowired
    private PlatformsService platformsService;

    /**
     * 获取平台列表（可选按分类过滤）
     * GET /platforms?categoryId=1
     */
    @GetMapping
    public R<List<Platforms>> list(@RequestHeader("X-User-Id") Long userId,
                                   @RequestParam(required = false) Long categoryId) {
        List<Platforms> list = platformsService.listByUserIdAndCategory(userId, categoryId);
        return R.success(list);
    }

    /**
     * 新增平台
     * POST /platforms
     * 请求体：{"name":"GitHub","url":"https://github.com","categoryId":1}
     */
    @PostMapping
    public R<Platforms> add(@RequestBody Map<String, Object> params,
                            @RequestHeader("X-User-Id") Long userId) {
        String name = (String) params.get("name");
        String url = (String) params.get("url");
        Long categoryId = params.get("categoryId") != null ?
                Long.valueOf(params.get("categoryId").toString()) : null;

        if (name == null || name.trim().isEmpty()) {
            return R.error("平台名称不能为空");
        }
        Platforms platform = platformsService.create(userId, name.trim(), url, categoryId);
        return R.success(platform);
    }

    /**
     * 删除平台
     * DELETE /platforms/{id}
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                          @RequestHeader("X-User-Id") Long userId) {
        platformsService.delete(userId, id);
        return "删除成功";
    }

    /**
     * 修改平台名称
     * PUT /platforms/{id}/name
     * 请求体：{"name":"新名称"}
     */
    @PutMapping("/{id}/name")
    public R<String> updateName(@PathVariable Long id,
                              @RequestBody Map<String, String> params,
                              @RequestHeader("X-User-Id") Long userId) {
        String newName = params.get("name");
        if (newName == null || newName.trim().isEmpty()) {
            return R.error("新名称不能为空");
        }
        platformsService.updateName(userId, id, newName.trim());
        return R.success("修改成功");
    }

    /**
     * 重新排序（全局排序）
     * PUT /platforms/order
     * 请求体：[5, 2, 8]  (按顺序排列的平台ID列表)
     */
    @PutMapping("/order")
    public R<Void> reorder(@RequestBody List<Long> platformIds,
                           @RequestHeader("X-User-Id") Long userId) {
        if (platformIds == null || platformIds.isEmpty()) {
            return R.error("平台ID列表不能为空");
        }
        platformsService.reorder(userId, platformIds);
        return R.success(null);
    }
}