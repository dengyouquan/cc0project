package com.dyq.demo.controller.api;

import com.dyq.demo.model.Resource;
import com.dyq.demo.model.ResourceStatus;
import com.dyq.demo.model.User;
import com.dyq.demo.service.ResourceService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.util.FastDFSClientWrapper;
import com.dyq.demo.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize) {
        List<Resource> resourceList = resourceService.findAll(pageIndex, pageSize);
        // todo 可以通过pageHelper的PageInfo得到总数量
        int resourceNum = resourceService.getCount();
        System.out.println("resourceNum:" + resourceNum);
        return ResponseEntity.ok().body(new Response(0, "资源列表", resourceNum, resourceList));
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "uploadresource";
    }

    @GetMapping(value = "/{id}")
    public String view(@PathVariable Long id, Model model) {
        Resource resource = resourceService.findById(id);
        model.addAttribute("resource", resource);
        return "/resourceDetail";
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        Resource resource = resourceService.findById(id);
        if (!resource.getFilePath().isEmpty()) {
            //在服务器上删除文件
            dfsClient.deleteFile(resource.getFilePath());
        }
        System.out.println("删除资源：" + id);
        resourceService.remove(id);
        return ResponseEntity.ok().body(new Response(0, "删除成功", 0, null));
    }

    @PostMapping
    //public ResponseEntity<Response> save(resource resource,BindingResult br) {
    //BindingResult 时间变成当前时间
    public ResponseEntity<Response> save(Resource resource) {
        User principal = userService.getUserByPrincipal();
        String msg = resource.getId() == null ? "新增成功!" : "更新成功!";
        Resource updateResource = null;
        boolean isUpdate = false;
        if (resource.getId() != null) {
            //更新
            isUpdate = true;
            updateResource = resourceService.findById(resource.getId());
            if (updateResource != null && !resource.getFilePath().equals(updateResource.getFilePath())) {
                //更新了图片地址,dfs删除图片
                dfsClient.deleteFile(updateResource.getFilePath());
            }
            System.out.println("updateResource:" + updateResource);
            updateResource.setDescription(resource.getDescription());
            updateResource.setFileFormat(resource.getFileFormat());
            updateResource.setFileName(resource.getFileName());
            updateResource.setFilePath(resource.getFilePath());
            updateResource.setFileSize(resource.getFileSize());
            updateResource.setStatus(resource.getStatus());
            updateResource.setUpdatedAt(new Date());
            resource = updateResource;
        }
        resource.setUserId(principal.getId());
        int code = 1;
        boolean saveSuccess = false;
        if (resource.getDescription() == null)
            msg = "描述为空，新增失败！";
        else if (resource.getFilePath() == null)
            msg = "文件路径为空，新增失败！";
        else if (resource.getFileName() == null)
            msg = "文件名为空，新增失败！";
        else if (resource.getFileSize() == null)
            msg = "文件大小为空，新增失败！";
        else {
            String[] arr = resource.getFilePath().split(".");
            if (arr.length > 0)
                resource.setFileFormat(arr[arr.length - 1]);
            resource.setStatus(ResourceStatus.NOT_VERIFY.value());
            resource.setIsEnabled(true);
            System.out.println("save resource:" + resource);
            resourceService.save(resource);
            saveSuccess = true;
            code = 0;
        }
        //resource更新时保存失败，dfs删除图片
        if (!saveSuccess && isUpdate) {
            //dfsClient.deleteFile(resource.getFilePath());
        }
        return ResponseEntity.ok().body(new Response(code, msg, 0, null));

    }
}
