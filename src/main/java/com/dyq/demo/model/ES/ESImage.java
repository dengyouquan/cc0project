package com.dyq.demo.model.ES;

import com.dyq.demo.model.Image;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
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
@Document(indexName = "cc0", type = "image")
public class ESImage implements Serializable {
    @Id  // 主键
    private String id;
    @Field(index = false, type = FieldType.Long)
    private Long esdocumentId;
    @Field(index = false, type = FieldType.Date)  // 不做全文检索字段
    private Date createdAt;
    @Field(index = false, type = FieldType.Date)  // 不做全文检索字段
    private Date updatedAt;
    //@Field(index = false,type = FieldType.text)
    private String fileName;
    private String description;
    private String filePath;
    private String fileFormat;
    private String fileSize;
    private String type;
    private String tags;
    @Field(index = false, type = FieldType.Long)
    private Long userId;

    public ESImage(Image image) {
        this.esdocumentId = image.getId();
        this.createdAt = image.getCreatedAt();
        this.updatedAt = image.getUpdatedAt();
        this.fileName = image.getFileName();
        this.description = image.getDescription();
        this.filePath = image.getFilePath();
        this.fileFormat = image.getFileFormat();
        this.fileSize = image.getFileSize();
        this.type = image.getType();
        this.tags = image.getTags();
    }

    public void update(Image image) {
        this.esdocumentId = image.getId();
        this.createdAt = image.getCreatedAt();
        this.updatedAt = image.getUpdatedAt();
        this.fileName = image.getFileName();
        this.description = image.getDescription();
        this.filePath = image.getFilePath();
        this.fileFormat = image.getFileFormat();
        this.fileSize = image.getFileSize();
        this.type = image.getType();
        this.tags = image.getTags();
    }
}
