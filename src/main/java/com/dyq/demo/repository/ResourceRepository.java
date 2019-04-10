package com.dyq.demo.repository;

import com.dyq.demo.model.Resource;
import com.dyq.demo.util.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface ResourceRepository extends MyMapper<Resource> {
    @Select("select count(*) from resource")
    int getCount();
}