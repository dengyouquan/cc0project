package com.dyq.demo.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * CreatedDate:2018/5/25
 * Author:dyq
 * FIXME 特别注意，该接口不能被扫描到，否则会出错
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
