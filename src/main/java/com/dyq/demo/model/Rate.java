package com.dyq.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class Rate extends BaseModel {
    private Integer rate;
    private Long resourceId;
    private Long userId;

    public Rate() {
        setUpdatedAt(new Date());
        if (getId() == null)
            setCreatedAt(new Date());
    }

    public static Rate getNullRate() {
        Rate rate = new Rate();
        rate.setCreatedAt(null);
        rate.setUpdatedAt(null);
        rate.setPage(null);
        rate.setRows(null);
        return rate;
    }
}
