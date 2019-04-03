package com.dyq.demo.model;

import lombok.*;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
public class Image extends BaseModel {
    @Column(name = "is_enabled")
    private Byte isEnabled;
    /**
     * elasticsearch ID
     */
    @Column(name = "e_id")
    private Byte eId;
    @Column(name = "file_name")
    private String fileName;
    private String description;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_format")
    private String fileFormat;
    @Column(name = "file_size")
    private String fileSize;
    private String type;
    private String tags;
    @Column(name = "user_id")
    private Long userId;

    public Image(){
        setUpdatedAt(new Date());
        if(getId()==null)
            setCreatedAt(new Date());
    }
}