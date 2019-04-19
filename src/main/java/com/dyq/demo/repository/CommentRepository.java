package com.dyq.demo.repository;

import com.dyq.demo.model.Comment;
import com.dyq.demo.util.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface CommentRepository extends MyMapper<Comment> {
}