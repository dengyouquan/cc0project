package com.dyq.demo.service;

import com.dyq.demo.model.ES.ESImage;
import com.dyq.demo.model.Image;
import com.dyq.demo.repository.ES.ESImageRepository;
import com.dyq.demo.repository.ImageRepository;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CreatedDate:2018/6/2
 * Author:dyq
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ESImageRepository esImageRepository;

    @Override
    public int getCount() {
        return imageRepository.getCount();
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.selectByPrimaryKey(id);
    }

    @Override
    public void save(Image image) {
        ESImage esImage = null;
        if(image.getId()!=null) {
            imageRepository.updateByPrimaryKey(image);
            esImage = esImageRepository.findByEsdocumentId(image.getId());
            esImage.update(image);
        } else {
            imageRepository.insert(image);
            //保存elasticsearch
            esImage = new ESImage(image);
        }
        //elasticsearch保存
        esImageRepository.save(esImage);

    }

    @Override
    public void remove(Long id) {
        imageRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<Image> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        return imageRepository.selectAll();
    }
}
