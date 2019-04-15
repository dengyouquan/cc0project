package com.dyq.demo.controller.search;

import com.dyq.demo.model.ES.ESImage;
import com.dyq.demo.model.ES.ESResource;
import com.dyq.demo.model.Image;
import com.dyq.demo.service.ES.ESImageService;
import com.dyq.demo.service.ES.ESResourceService;
import com.dyq.demo.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * CreatedDate:2018/6/16
 * Author:dyq
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ESImageService esImageService;
    @Autowired
    private ESResourceService esResourceService;

    @GetMapping("/image/list")
    public ResponseEntity<Response> getAllImages(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize,
                                                 @RequestParam(value = "type", required = false, defaultValue = "all") String type,
                                                 @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        List<ESImage> esimageList = esImageService.findAll(type, keyword, pageIndex, pageSize).getContent();
        long esimageNum = esImageService.count();
        System.out.println("esimageList:" + esimageList);
        System.out.println("esimageNum:" + esimageNum);
        return ResponseEntity.ok().body(new Response(0, "图片列表", (int) esimageNum, esimageList));
    }

    @GetMapping("/resource/list")
    public ResponseEntity<Response> getAllResources(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                                    @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize,
                                                    @RequestParam(value = "type", required = false, defaultValue = "all") String type,
                                                    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        List<ESResource> esResourceList = esResourceService.findAll(type, keyword, pageIndex, pageSize).getContent();
        long esResourceNum = esResourceService.count();
        System.out.println("esResourceList:" + esResourceList);
        System.out.println("esResourceNum:" + esResourceNum);
        return ResponseEntity.ok().body(new Response(0, "图片列表", (int) esResourceNum, esResourceList));
    }
}
