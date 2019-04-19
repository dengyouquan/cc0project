package com.dyq.demo.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class Comment extends BaseModel {
    private String fromAvatar;
    private String fromName;
    private String content;
    private Long userId;
    private Long resourceId;
    private Boolean enabled;

    public Comment() {
        setUpdatedAt(new Date());
        if (getId() == null)
            setCreatedAt(new Date());
    }

    public static Comment getNullComment(){
        Comment comment = new Comment();
        comment.setCreatedAt(null);
        comment.setUpdatedAt(null);
        comment.setPage(null);
        comment.setRows(null);
        return comment;
    }
}
