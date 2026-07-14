package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Categories;
import org.example.entity.dto.CategorySort;

import java.util.List;

@Mapper
public interface CategoriesMapper extends BaseMapper<Categories> {

    /**
     * 查询某用户所有分类，按 sort_order 升序
     */
    List<Categories> selectByUserIdOrderBySort(@Param("userId") Long userId);

    /**
     * 查询某用户下的最大排序值
     */
    Integer selectMaxSortOrderByUserId(@Param("userId") Long userId);

    /**
     * 批量更新分类排序
     */
    void batchUpdateSortOrder(@Param("list") List<CategorySort> list);

    boolean hasName(@Param("userId") Long userId,@Param("name") String name);
}