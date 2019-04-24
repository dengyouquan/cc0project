package com.dyq.demo.service.ES;

import com.dyq.demo.model.ES.ESImage;
import com.dyq.demo.model.ES.ESResource;
import com.dyq.demo.repository.ES.ESResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsResourceServiceImpl implements ESResourceService {
    @Autowired
    private ESResourceRepository esResourceRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public ESResource findById(String id) {
        return esResourceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ESResource esResource) {
        esResourceRepository.save(esResource);
    }

    @Override
    public void remove(String id) {
        esResourceRepository.deleteById(id);
    }

    @Override
    public List<ESResource> findAll(Integer pageNum, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNum - 1, pageSize);
        return esResourceRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<ESResource> findAll(String type, String keyword, Pageable pageable) {
        Page<ESResource> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        //判断是否有类型
        if (type == null || type.equalsIgnoreCase("") || type.equalsIgnoreCase("all")) {
            pages = esResourceRepository.findDistinctByFileNameLikeOrDescriptionLike(keyword, keyword, pageable);
        } else {
            pages = esResourceRepository.findDistinctByTypeAndFileNameLikeOrDescriptionLike(type, keyword, keyword, pageable);
        }
        return pages;
    }

    @Override
    public Page<ESResource> findAll(String type, String keyword, Integer pageNum, Integer pageSize) {
        Page<ESResource> pages = null;
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
            //PageRequest.of(pageNum, pageSize) 代替
            PageRequest pageable = new PageRequest(pageNum - 1, pageSize, sort);
            //判断是否有类型
            if (type == null || type.equalsIgnoreCase("") || type.equalsIgnoreCase("all")) {
                pages = esResourceRepository.findDistinctByFileNameLikeOrDescriptionLike(keyword, keyword, pageable);
            } else {
                pages = esResourceRepository.findDistinctByTypeAndFileNameLikeOrDescriptionLike(type, keyword, keyword, pageable);
            }
        } catch (Exception e) {
            //搜索会有异常Resolved exception caused by Handler execution: org.springframework.dao.InvalidDataAccessApiUsageException: Cannot constructQuery '*"3D音乐馆 - 3DFade 小清新电音"'. Use expression or multiple clauses instead.
        }
        return pages;
    }

    @Override
    public ESResource findByEsdocumentId(Long esdocumentId) {
        return esResourceRepository.findByEsdocumentId(esdocumentId);
    }

    @Override
    public long count() {
        return esResourceRepository.count();
    }
}
