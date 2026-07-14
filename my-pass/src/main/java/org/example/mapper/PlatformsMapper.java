package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Platforms;
import org.example.entity.dto.PlatformSort;

import java.util.List;

@Mapper
public interface PlatformsMapper extends BaseMapper<Platforms> {

    /**
     * 查询用户平台列表，可按分类过滤，按 sort_order 升序
     */
    List<Platforms> selectByUserIdAndCategory(@Param("userId") Long userId,
                                              @Param("categoryId") Long categoryId);

    /**
     * 查询用户下最大排序值
     */
    Integer selectMaxSortOrderByUserId(@Param("userId") Long userId);

    /**
     * 批量更新排序
     */
    void batchUpdateSortOrder(@Param("list") List<PlatformSort> list);
}