package com.dyq.demo.service;

import com.dyq.demo.model.BaseModel;
import com.dyq.demo.util.MyMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * CreatedDate:2018/6/2
 * Author:dyq
 */
public class BaseModelServiceImpl<T extends BaseModel> implements BaseModelService<T> {
    @Autowired
    protected MyMapper<T> repository;

    @Override
    public T findById(Long id) {
        return repository.selectByPrimaryKey(id);
    }

    @Override
    public void save(T t) {
        if(t.getId()!=null) {
            repository.updateByPrimaryKey(t);
        } else {
            repository.insert(t);
        }
    }

    @Override
    public void remove(Long id) {
        repository.deleteByPrimaryKey(id);
    }

    @Override
    public List<T> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        return repository.selectAll();
    }
}
