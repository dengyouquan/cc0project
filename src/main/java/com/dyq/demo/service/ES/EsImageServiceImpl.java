package com.dyq.demo.service.ES;

import com.dyq.demo.model.ES.ESImage;
import com.dyq.demo.repository.ES.ESImageRepository;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CreatedDate:2018/6/16
 * Author:dyq
 */
@Service
public class EsImageServiceImpl implements ESImageService {
    @Autowired
    private ESImageRepository esImageRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public ESImage findById(String id) {
        return esImageRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ESImage esImage) {
        esImageRepository.save(esImage);
    }

    @Override
    public void remove(String id) {
        esImageRepository.deleteById(id);
    }

    @Override
    public List<ESImage> findAll(Integer pageNum, Integer pageSize) {
        PageRequest pageable = new PageRequest(pageNum, pageSize);
        return esImageRepository.findAll(pageable).getContent();
    }

    @Override
    public Page<ESImage> findAll(String type, String keyword, Pageable pageable) {
        Page<ESImage> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"createdAt");
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        //判断是否有类型
        if(type==null || type.equalsIgnoreCase("") || type.equalsIgnoreCase("all")){
            pages = esImageRepository.findDistinctByFileNameContainingOrDescriptionContaining(keyword,keyword,pageable);
        }else{
            pages = esImageRepository.findDistinctByTypeAndFileNameContainingOrDescriptionContaining(type,keyword,keyword,pageable);
        }
        return pages;
    }

    @Override
    public Page<ESImage> findAll(String type, String keyword, Integer pageNum, Integer pageSize) {
        Page<ESImage> pages = null;
        Sort sort = new Sort(Sort.Direction.DESC,"createdAt");
        //PageRequest.of(pageNum, pageSize) 代替
        PageRequest pageable = new PageRequest(pageNum, pageSize, sort);
        //判断是否有类型
        if(type==null || type.equalsIgnoreCase("") || type.equalsIgnoreCase("all")){
            pages = esImageRepository.findDistinctByFileNameContainingOrDescriptionContaining(keyword,keyword,pageable);
        }else{
            pages = esImageRepository.findDistinctByTypeAndFileNameContainingOrDescriptionContaining(type,keyword,keyword,pageable);
        }
        return pages;
    }

    @Override
    public ESImage findByEsdocumentId(Long esdocumentId) {
        return esImageRepository.findByEsdocumentId(esdocumentId);
    }

    @Override
    public long count() {
        return esImageRepository.count();
    }
}
