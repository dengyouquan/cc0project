package com.dyq.demo.repository.ES;

import com.dyq.demo.model.ES.ESResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ESResourceRepository extends ElasticsearchRepository<ESResource, String> {
    ESResource findByEsdocumentId(Long esdocumentId);

    void deleteByEsdocumentId(Long esdocumentId);

    Page<ESResource> findDistinctByFileNameLikeOrDescriptionLike(String fileName, String description, Pageable pageable);

    Page<ESResource> findDistinctByTypeAndFileNameLikeOrDescriptionLike(String type, String fileName, String description, Pageable pageable);

    List<ESResource> findByFileNameLike(String fileName);

    List<ESResource> findByFileNameContains(String fileName);

    List<ESResource> findByFileNameIsContaining(String fileName);
}
