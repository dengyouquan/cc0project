package com.dyq.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class Resource extends BaseModel {
    @Column(name = "is_enabled")
    private Boolean isEnabled;
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
    @Column(name = "total_rate")
    private Long totalRate;
    @Column(name = "total_rate_num")
    private Integer totalRateNum;
    @Transient
    private Integer averageRate;

    public Resource() {
        setUpdatedAt(new Date());
        if (getId() == null)
            setCreatedAt(new Date());
    }

    public static Resource getNullResource() {
        Resource resource = new Resource();
        resource.setUpdatedAt(null);
        resource.setCreatedAt(null);
        resource.setIsEnabled(null);
        resource.setStatus(null);
        resource.setFileFormat(null);
        resource.setDescription(null);
        resource.setFileName(null);
        resource.setFilePath(null);
        resource.setFileSize(null);
        resource.setUserId(null);
        resource.setEId(null);
        resource.setStyle(null);
        resource.setTags(null);
        resource.setId(null);
        resource.setType(null);
        resource.setPage(null);
        resource.setRows(null);
        return resource;
    }
}