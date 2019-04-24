package com.dyq.demo.controller.api;

import com.dyq.demo.model.Rate;
import com.dyq.demo.model.Resource;
import com.dyq.demo.model.User;
import com.dyq.demo.service.RateService;
import com.dyq.demo.service.ResourceService;
import com.dyq.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengyouquan
 * @createTime 2019-04-20
 **/
@RestController
@RequestMapping("/api/rates")
public class RateController {
    @Autowired
    private RateService rateService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @GetMapping
    public Rate rate(@RequestParam(value = "resource_id") Long resourceId) {
        User user = userService.getUserByPrincipal();
        if (user == null) {
            return null;
        }
        return rateService.getRateByUserIdAndResourceId(user.getId(), resourceId);
    }

    //@PreAuthorize("isAuthenticated()")
    @PostMapping
    @Transactional
    public ResponseEntity<Rate> rate(@RequestParam(value = "rate") Integer rateScore, @RequestParam(value = "resource_id") Long resourceId) {
        User user = userService.getUserByPrincipal();
        if (user == null) {
            throw new RuntimeException("未登录");
        }
        Rate findRate = rateService.getRateByUserIdAndResourceId(user.getId(), resourceId);
        if (findRate != null) {
            //不能重复评论
            return ResponseEntity.ok().body(findRate);
        }
        Rate rate = new Rate();
        rate.setResourceId(resourceId);
        rate.setUserId(user.getId());
        rate.setRate(rateScore);
        rateService.save(rate);
        //保存评分，可以存在Redis，然后定期刷新
        Resource resource = resourceService.findById(resourceId);
        resource.setTotalRateNum(resource.getTotalRateNum() + 1);
        resource.setTotalRate(resource.getTotalRate() + rateScore);
        resourceService.save(resource);
        return ResponseEntity.ok().body(rate);
    }
}
