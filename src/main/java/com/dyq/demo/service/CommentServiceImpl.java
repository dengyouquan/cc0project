package com.dyq.demo.service;

import com.dyq.demo.model.Comment;
import com.dyq.demo.repository.CommentRepository;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.selectByPrimaryKey(id);
    }

    @Override
    public void save(Comment comment) {
        if (comment.getId() != null) {
            commentRepository.updateByPrimaryKey(comment);
        } else {
            commentRepository.insert(comment);
        }
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<Comment> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        Comment comment = Comment.getNullComment();
        comment.setEnabled(true);
        return commentRepository.select(comment);
    }

    @Override
    public List<Comment> findAllByResourceId(Long resourceId, Integer pageNum, Integer pageSize, String orderBy) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        Comment comment = Comment.getNullComment();
        comment.setResourceId(resourceId);
        comment.setEnabled(true);
        return commentRepository.select(comment);
    }

    @Override
    public long countByResourceId(Long resourceId) {
        Comment comment = Comment.getNullComment();
        comment.setEnabled(true);
        comment.setResourceId(resourceId);
        return commentRepository.selectCount(comment);
    }
}
