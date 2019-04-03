package com.dyq.demo.controller;

import com.dyq.demo.model.Image;
import com.dyq.demo.service.ImageService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.util.FastDFSClientWrapper;
import com.dyq.demo.util.FileUtil;
import com.dyq.demo.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * CreatedDate:2018/6/4
 * Author:dyq
 */
@Controller
public class UploadController {
    @Autowired
    UserService userService;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    @Autowired
    private ImageService imageService;

    // 上传文件
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    public ResponseEntity<Response> upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileUrl = dfsClient.uploadFile(file);
        int size = (int)file.getSize()/1024;//KB
        System.out.println("size:"+size);
        return ResponseEntity.ok().body(new Response(0,"上传成功",size,fileUrl));
    }
    // 下载文件
    @GetMapping(value = "/download/image/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        Image image = imageService.findById(id);
        String fileUrl = image.getFilePath();
        String fileName = image.getFileName();
        byte[] content = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            //TODO 下载文件时点X，不能再次下下载
            content =dfsClient.downloadFile(fileUrl);
            headers.setContentDispositionFormData("attachment",  new String(fileName.getBytes("UTF-8"),"iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/upload/delete/image")
    public ResponseEntity<Response> delete() throws Exception {
        String fileUrl = "http://118.89.163.211:81/M00/00/00/rBEABlsWg-mAQBKDAAAXaU7Mr6E761.jpg";
        dfsClient.deleteFile(fileUrl);
        return ResponseEntity.ok().body(new Response(0,"删除成功",0,fileUrl));
    }

    //处理文件上传
    @PostMapping("/uploadfile")
    public ResponseEntity<Response> uploadfile(@RequestParam("file") MultipartFile file,
                                               HttpServletRequest request) {
        String contentType = file.getContentType();
        String fileName = getFileName(file);
        System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);
        String filePath=request.getSession().getServletContext().getRealPath("/file/");
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json
        System.out.println("/file/"+fileName);
        return ResponseEntity.ok().body(new Response(0,"",0,"/file/"+fileName));
    }

    private String getFileName(MultipartFile file){
        long l = System.currentTimeMillis();
        String s = Long.toString(l);
        if(s.length()>7)
            s = s.substring(s.length()-7, s.length());
        else
            s = s+s;
        String fileName = file.getOriginalFilename();
        fileName = fileName +s +"-"+file.getOriginalFilename();
        System.out.println("fileName:"+fileName);
        /*//上传
        file.transferTo(new File(path));
        path = path.split("GameDemo")[1];
        //path = path.substring(1, path.length());
        path = path.replace("\\","/");
        path = path.substring(1, path.length());
        System.out.println("path:"+path);*/
        return fileName;
    }
}
