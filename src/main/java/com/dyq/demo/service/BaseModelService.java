package com.dyq.demo.service;

import com.dyq.demo.model.BaseModel;

import java.util.List;

public interface BaseModelService<T extends BaseModel> {
    T findById(Long id);
    void save(T t);
    void remove(Long id);
    List<T> findAll(Integer pageNum, Integer pageSize);
}
