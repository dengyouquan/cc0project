package com.dyq.demo.repository.ES;

import com.dyq.demo.model.ES.ESResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESResourceRepository extends ElasticsearchRepository<ESResource, String> {
    ESResource findByEsdocumentId(Long esdocumentId);

    Page<ESResource> findDistinctByFileNameContainingOrDescriptionContaining(String fileName, String description, Pageable pageable);

    Page<ESResource> findDistinctByTypeAndFileNameContainingOrDescriptionContaining(String type, String fileName, String description, Pageable pageable);
}
