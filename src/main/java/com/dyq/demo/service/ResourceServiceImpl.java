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
    public Resource findById(Long id) {
        return resourceRepository.selectByPrimaryKey(id);
    }

    @Override
    public void save(Resource resource) {
        ESResource esResource = null;
        if (resource.getId() != null) {
            resourceRepository.updateByPrimaryKey(resource);
            esResource = esResourceRepository.findByEsdocumentId(resource.getId());
            esResource.update(resource);
        } else {
            resourceRepository.insert(resource);
            //保存elasticsearch
            esResource = new ESResource(resource);
        }
        //elasticsearch保存
        esResourceRepository.save(esResource);

    }

    @Override
    public void remove(Long id) {
        resourceRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<Resource> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        return resourceRepository.selectAll();
    }
}
