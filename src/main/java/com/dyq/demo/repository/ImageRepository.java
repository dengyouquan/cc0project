package com.dyq.demo.repository;

import com.dyq.demo.model.Image;
import com.dyq.demo.util.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface ImageRepository extends MyMapper<Image> {
    @Select("select count(*) from image")
    int getCount();
}