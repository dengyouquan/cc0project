package com.dyq.demo.service;

import com.dyq.demo.model.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentService extends BaseModelService<Comment> {
    List<Comment> findAllByResourceId(Long resourceId, Integer pageNum, Integer pageSize, String orderBy);

    long countByResourceId(Long resourceId);
}
