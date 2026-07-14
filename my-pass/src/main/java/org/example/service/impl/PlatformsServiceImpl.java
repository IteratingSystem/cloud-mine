package org.example.service.impl;

import org.example.entity.Platforms;
import org.example.entity.dto.PlatformSort;
import org.example.exception.BusinessException;
import org.example.mapper.PlatformsMapper;
import org.example.service.PlatformsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlatformsServiceImpl implements PlatformsService {

    @Autowired
    private PlatformsMapper platformsMapper;

    @Override
    public List<Platforms> listByUserIdAndCategory(Long userId, Long categoryId) {
        return platformsMapper.selectByUserIdAndCategory(userId, categoryId);
    }

    @Override
    public Platforms create(Long userId, String name, String url, Long categoryId) {
        // 校验同名平台（可选）
        // 计算排序值
        Integer maxSort = platformsMapper.selectMaxSortOrderByUserId(userId);
        int sortOrder = (maxSort == null) ? 0 : maxSort + 1;

        Platforms platform = new Platforms();
        platform.setUserId(userId);
        platform.setName(name);
        platform.setUrl(url);
        platform.setCategoryId(categoryId);
        platform.setSortOrder(sortOrder);

        platformsMapper.insert(platform);
        return platformsMapper.selectById(platform.getId());
    }

    @Override
    public void delete(Long userId, Long platformId) {
        Platforms platform = platformsMapper.selectById(platformId);
        if (platform == null || !platform.getUserId().equals(userId)) {
            throw new BusinessException("平台不存在或无权删除");
        }
        platformsMapper.deleteById(platformId);
    }

    @Override
    public void updateName(Long userId, Long platformId, String newName) {
        Platforms platform = platformsMapper.selectById(platformId);
        if (platform == null || !platform.getUserId().equals(userId)) {
            throw new BusinessException("平台不存在或无权修改");
        }
        platform.setName(newName);
        platformsMapper.updateById(platform);
    }

    @Override
    public void reorder(Long userId, List<Long> platformIds) {
        // 校验所有平台属于该用户
        List<Platforms> existing = platformsMapper.selectBatchIds(platformIds);
        if (existing.size() != platformIds.size()) {
            throw new BusinessException("部分平台不存在");
        }
        for (Platforms p : existing) {
            if (!p.getUserId().equals(userId)) {
                throw new BusinessException("无权操作其他用户的平台");
            }
        }

        List<PlatformSort> sortList = new ArrayList<>();
        for (int i = 0; i < platformIds.size(); i++) {
            PlatformSort sort = new PlatformSort();
            sort.setId(platformIds.get(i));
            sort.setSortOrder(i);
            sortList.add(sort);
        }
        platformsMapper.batchUpdateSortOrder(sortList);
    }
}