package com.dyq.demo.service.ES;

import com.dyq.demo.model.ES.ESImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * CreatedDate:2018/6/16
 * Author:dyq
 */
public interface ESImageService {
    ESImage findById(String id);
    void save(ESImage esImage);
    void remove(String id);
    List<ESImage> findAll(Integer pageNum, Integer pageSize);
    Page<ESImage> findAll(String type, String keyword, Pageable pageable);
    Page<ESImage> findAll(String type, String keyword, Integer pageNum, Integer pageSize);
    ESImage findByEsdocumentId(Long esdocumentId);
    long count();
}
