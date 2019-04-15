package com.dyq.demo.model.ES;

import com.dyq.demo.model.Image;
import com.dyq.demo.model.Resource;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * CreatedDate:2018/6/16
 * Author:dyq
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "cc0", type = "resource")
public class ESResource implements Serializable {
    @Id  // 主键
    private String id;
    @Field(index = false, type = FieldType.Long)
    private Long esdocumentId;
    @Field(index = false, type = FieldType.Date)  // 不做全文检索字段
    private Date createdAt;
    @Field(index = false, type = FieldType.Date)  // 不做全文检索字段
    private Date updatedAt;
    @Field(type = FieldType.text)
    private String fileName;
    @Field(type = FieldType.text)
    private String description;
    private String filePath;
    private String fileFormat;
    private String fileSize;
    private String type;
    private String style;
    private String tags;
    @Field(index = false, type = FieldType.Long)
    private Long userId;
    private Integer status;

    public ESResource(Resource resource) {
        update(resource);
    }

    public void update(Resource resource) {
        this.esdocumentId = resource.getId();
        this.createdAt = resource.getCreatedAt();
        this.updatedAt = resource.getUpdatedAt();
        this.fileName = resource.getFileName();
        this.description = resource.getDescription();
        this.filePath = resource.getFilePath();
        this.fileFormat = resource.getFileFormat();
        this.fileSize = resource.getFileSize();
        this.type = resource.getType();
        this.tags = resource.getTags();
        this.status = resource.getStatus();
        this.style = resource.getStyle();
    }
}
