package com.dyq.demo.service;

import com.dyq.demo.model.Rate;

public interface RateService extends BaseModelService<Rate> {
    Rate getRateByUserIdAndResourceId(Long userId, Long resourceId);
}
