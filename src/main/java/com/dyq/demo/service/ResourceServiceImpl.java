package com.dyq.demo.service;

import com.dyq.demo.model.ES.ESResource;
import com.dyq.demo.model.Resource;
import com.dyq.demo.repository.ES.ESResourceRepository;
import com.dyq.demo.repository.ResourceRepository;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ESResourceRepository esResourceRepository;

    @Override
    public int getCount() {
        return resourceRepository.getCount();
    }

    @Override
    public int getCountByStatus(int status) {
        return resourceRepository.getCountByStatus(status);
    }

    @Override
    public int getCountByUserId(long userId) {
        return resourceRepository.getCountByUserId(userId);
    }

    @Override
    public List<Resource> findAllByStatus(Integer pageNum, Integer pageSize, int status) {
        return findAllByUserIdAndStatus(pageNum, pageSize, null, status);
    }

    @Override
    public List<Resource> findAllByUserId(Integer pageNum, Integer pageSize, long userId) {
        return findAllByUserIdAndStatus(pageNum, pageSize, userId, null);
    }

    @Override
    public List<Resource> findAllByUserIdAndStatus(Integer pageNum, Integer pageSize, Long userId, Integer status) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        Resource resource = Resource.getNullResource();
        resource.setIsEnabled(true);
        resource.setStatus(status);
        resource.setUserId(userId);
        return resourceRepository.select(resource);
    }

    @Override
    public Resource findById(Long id) {
        return resourceRepository.selectByPrimaryKey(id);
    }

    @Override
    public void save(Resource resource) {
        ESResource esResource = null;
        if (resource.getId() != null) {
            resourceRepository.updateByPrimaryKey(resource);
            //elasticSearch 有问题
            //esResource = esResourceRepository.findByEsdocumentId(resource.getId());
            //esResource.update(resource);
        } else {
            resourceRepository.insert(resource);
            //保存elasticsearch
            esResource = new ESResource(resource);
            //elasticsearch保存
            esResourceRepository.save(esResource);
        }

    }

    @Override
    public void remove(Long id) {
        resourceRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<Resource> findAll(Integer pageNum, Integer pageSize) {
        return findAllByUserIdAndStatus(pageNum, pageSize, null, null);
    }
}
