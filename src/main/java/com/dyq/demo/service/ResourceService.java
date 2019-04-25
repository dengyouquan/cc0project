package com.dyq.demo.service;

import com.dyq.demo.model.Resource;

import java.util.List;

public interface ResourceService extends BaseModelService<Resource> {
    int getCount();

    int getCountByStatus(int status);


    List<Resource> findAll(Integer pageNum, Integer pageSize, Integer status);
}
