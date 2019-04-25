package com.dyq.demo.service;

import com.dyq.demo.model.Resource;

import java.util.List;

public interface ResourceService extends BaseModelService<Resource> {
    int getCount();

    int getCountByStatus(int status);

    int getCountByUserId(long userId);

    List<Resource> findAllByStatus(Integer pageNum, Integer pageSize, int status);

    List<Resource> findAllByUserId(Integer pageNum, Integer pageSize, long userId);

    List<Resource> findAllByUserIdAndStatus(Integer pageNum, Integer pageSize, Long userId, Integer status);
}
