package com.dyq.demo.model;

/**
 * @author dengyouquan
 * @createTime 2019-04-15
 **/
public enum ResourceStatus {
    NOT_VERIFY(0), VERIFY_PASS(1), VERIFY_NOT_PASS(-1);
    private Integer status;

    ResourceStatus(Integer status) {
        this.status = status;
    }

    public Integer value() {
        return status;
    }}
