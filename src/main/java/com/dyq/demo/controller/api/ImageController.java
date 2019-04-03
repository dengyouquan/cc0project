package com.dyq.demo.controller.api;

import com.dyq.demo.model.Image;
import com.dyq.demo.model.User;
import com.dyq.demo.service.ImageService;
import com.dyq.demo.util.FastDFSClientWrapper;
import com.dyq.demo.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * CreatedDate:2018/6/2
 * Author:dyq
 */
//@RestController
@Controller
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    /*@RequestMapping
    public PageInfo<Image> getAll() {
        List<Image> imageList = imageService.findAll(1,10);
        return new PageInfo<Image>(imageList);
    }*/

    /*@RequestMapping
    public String list() {
        return "/api/image";
    }*/

   @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value="page",required=false,defaultValue="1") int pageIndex,
                                              @RequestParam(value="limit",required=false,defaultValue="10") int pageSize){
       List<Image> imageList = imageService.findAll(pageIndex,pageSize);
       // todo 可以通过pageHelper的PageInfo得到总数量
        int imageNum = imageService.getCount();
        System.out.println("imageNum:"+imageNum);
        return ResponseEntity.ok().body(new Response(0,"图片列表",imageNum,imageList));
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "uploadimage";
    }

    @RequestMapping(value = "/view/{id}")
    public String view(@PathVariable Long id,Model model) {
        Image image = imageService.findById(id);
        model.addAttribute("image",image);
        return "/imageDetail";
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        Image image = imageService.findById(id);
        if(!image.getFilePath().isEmpty()){
            //在服务器上删除文件
            dfsClient.deleteFile(image.getFilePath());
        }
        System.out.println("删除图片："+id);
        imageService.remove(id);
        return ResponseEntity.ok().body(new Response(0,"删除成功",0,null));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    //public ResponseEntity<Response> save(Image image,BindingResult br) {
    //BindingResult 时间变成当前时间
    public ResponseEntity<Response> save(Image image) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String msg = image.getId() == null ? "新增成功!" : "更新成功!";
        Image updateImage = null;
        boolean isUpdate = false;
        if(image.getId()!=null){
            //更新
            isUpdate = true;
            updateImage = imageService.findById(image.getId());
            if(updateImage!=null && !image.getFilePath().equals(updateImage.getFilePath())){
                //更新了图片地址,dfs删除图片
                dfsClient.deleteFile(updateImage.getFilePath());
            }
            System.out.println("updateImage:"+updateImage);
            updateImage.setDescription(image.getDescription());
            updateImage.setFileFormat(image.getFileFormat());
            updateImage.setFileName(image.getFileName());
            updateImage.setFilePath(image.getFilePath());
            updateImage.setFileSize(image.getFileSize());
            updateImage.setUpdatedAt(new Date());
            image = updateImage;
        }
        image.setUserId(principal.getId());
        int code = 1;
        boolean saveSuccess = false;
        if(image.getDescription()==null)
            msg = "描述为空，新增失败！";
        else if(image.getFilePath()==null)
            msg = "文件路径为空，新增失败！";
        else if(image.getFileName()==null)
            msg = "文件名为空，新增失败！";
        else if(image.getFileSize()==null)
            msg = "文件大小为空，新增失败！";
        else{
            String[] arr = image.getFilePath().split(".");
            if(arr.length>0)
                image.setFileFormat(arr[arr.length-1]);
            System.out.println("save image:"+image);
            imageService.save(image);
            saveSuccess = true;
            code = 0;
        }
        //image更新时保存失败，dfs删除图片
        if(!saveSuccess && isUpdate){
            //dfsClient.deleteFile(image.getFilePath());
        }
        return ResponseEntity.ok().body(new Response(code,msg,0,null));

    }
}
