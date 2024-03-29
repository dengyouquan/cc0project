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
    public List<Resource> findAllByStatus(Integer pageNum, Integer pageSize, int status, String orderBy) {
        return findAllByUserIdAndStatus(pageNum, pageSize, null, status, orderBy);
    }

    @Override
    public List<Resource> findAll(Integer pageNum, Integer pageSize, String orderBy) {
        return findAllByUserIdAndStatus(pageNum, pageSize, null, null, orderBy);
    }

    @Override
    public List<Resource> findAllByUserId(Integer pageNum, Integer pageSize, long userId) {
        return findAllByUserIdAndStatus(pageNum, pageSize, userId, null, null);
    }

    @Override
    public List<Resource> findAllByUserIdAndStatus(Integer pageNum, Integer pageSize, Long userId, Integer status, String orderBy) {
        if (pageNum != null && pageSize != null) {
            if (orderBy != null) {
                PageHelper.startPage(pageNum, pageSize, orderBy);
            } else {
                PageHelper.startPage(pageNum, pageSize);
            }
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
            update(resource, false);
        } else {
            //保存elasticsearch
            esResource = new ESResource(resource);
            //elasticsearch保存
            esResourceRepository.save(esResource);
            resource.setEId(esResource.getId());
            resourceRepository.insert(resource);
        }
    }

    @Override
    public void update(Resource resource, boolean deletedEsResource) {
        esResourceRepository.deleteById(resource.getEId());
        ESResource esResource = new ESResource(resource);
        if (!deletedEsResource) {
            esResourceRepository.save(esResource);
        }
        resource.setEId(esResource.getId());
        resourceRepository.updateByPrimaryKey(resource);
    }

    @Override
    public void remove(Long id) {
        Resource query = Resource.getNullResource();
        query.setId(id);
        Resource resource = resourceRepository.selectOne(query);
        esResourceRepository.deleteById(resource.getEId());
        resourceRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<Resource> findAll(Integer pageNum, Integer pageSize) {
        return findAllByUserIdAndStatus(pageNum, pageSize, null, null, null);
    }
}
