package com.dyq.demo.service;

import com.dyq.demo.model.Rate;
import com.dyq.demo.repository.RateRepository;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateRepository rateRepository;

    @Override
    public Rate findById(Long id) {
        return rateRepository.selectByPrimaryKey(id);
    }

    @Override
    public void save(Rate rate) {
        if (rate.getId() != null) {
            rateRepository.updateByPrimaryKey(rate);
        } else {
            rateRepository.insert(rate);
        }
    }

    @Override
    public void remove(Long id) {
        rateRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<Rate> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        Rate rate = Rate.getNullRate();
        return rateRepository.select(rate);
    }

    @Override
    public Rate getRateByUserIdAndResourceId(Long userId, Long resourceId) {
        Rate rate = Rate.getNullRate();
        rate.setUserId(userId);
        rate.setResourceId(resourceId);
        return rateRepository.selectOne(rate);
    }
}
