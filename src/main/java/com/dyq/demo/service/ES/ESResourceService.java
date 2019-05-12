package com.dyq.demo.service.ES;

import com.dyq.demo.model.ES.ESResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ESResourceService {
    ESResource findById(String id);

    void save(ESResource esResource);

    void remove(String id);

    List<ESResource> findAll(Integer pageNum, Integer pageSize);

    Page<ESResource> findAll(String type, String keyword, Pageable pageable);

    Page<ESResource> findAll(String type, String keyword, Integer pageNum, Integer pageSize);

    ESResource findByEsdocumentId(Long esdocumentId);

    void deleteByEsdocumentId(Long esdocumentId);

    long count();
}
