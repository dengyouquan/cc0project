package com.dyq.demo.repository.ES;

import com.dyq.demo.model.ES.ESImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * CreatedDate:2018/6/16
 * Author:dyq
 */
public interface ESImageRepository extends ElasticsearchRepository<ESImage, String> {
    ESImage findByEsdocumentId(Long esdocumentId);
    Page<ESImage> findDistinctByFileNameContainingOrDescriptionContaining(String fileName,String description, Pageable pageable);
    Page<ESImage> findDistinctByTypeAndFileNameContainingOrDescriptionContaining(String type,String fileName,String description, Pageable pageable);
}
