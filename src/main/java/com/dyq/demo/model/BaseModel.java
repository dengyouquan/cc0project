package com.dyq.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * An abstract base model class for entities
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class BaseModel implements Comparable<BaseModel>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private Date updatedAt;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    //mybatis无效 jpa的东西
    @PrePersist
    public void prePersist(){
        createdAt = updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = new Date();
    }

    @Override
    public int compareTo(BaseModel o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || ((BaseModel) other).getId()==null || other.getClass() != this.getClass())
            return false;
        Long id = ((BaseModel) other).getId();
        return this.getId().equals(id);
    }
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
}