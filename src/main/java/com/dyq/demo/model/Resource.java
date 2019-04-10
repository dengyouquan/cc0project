package com.dyq.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class Resource extends BaseModel {
    @Column(name = "is_enabled")
    private Byte isEnabled;
    /**
     * elasticsearch ID
     */
    @Column(name = "e_id")
    private Long eId;
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
    private String style;
    private String tags;
    @Column(name = "user_id")
    private Long userId;
    private Integer status;

    public Resource(){
        setUpdatedAt(new Date());
        if(getId()==null)
            setCreatedAt(new Date());
    }
}