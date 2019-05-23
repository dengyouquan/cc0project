package com.dyq.demo.controller.api;

import com.dyq.demo.model.ES.ESResource;
import com.dyq.demo.model.Resource;
import com.dyq.demo.model.ResourceStatus;
import com.dyq.demo.model.User;
import com.dyq.demo.service.ES.ESResourceService;
import com.dyq.demo.service.ResourceService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.util.FastDFSClientWrapper;
import com.dyq.demo.vo.Response;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private ESResourceService esResourceService;
    @Autowired
    private UserService userService;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    @ResponseBody
    @PutMapping("/{id}/agree")
    public ResponseEntity agree(@PathVariable Long id) {
        System.out.println(id + "审核通过");
        Resource resource = resourceService.findById(id);
        if (resource != null) {
            resource.setStatus(1);
            resource.setIsEnabled(true);
            resourceService.update(resource, false);
        }
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PutMapping("/{id}/oppose")
    public ResponseEntity oppose(@PathVariable Long id) {
        System.out.println(id + "审核不通过");
        Resource resource = resourceService.findById(id);
        if (resource != null) {
            resource.setStatus(-1);
            resource.setIsEnabled(false);
            resourceService.update(resource, true);
        }
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<Response> getAllByUser(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize) {
        List<Resource> resourceList;
        int resourceNum;
        User user = userService.getUserByPrincipal();
        resourceList = resourceService.findAllByUserId(pageIndex, pageSize, user.getId());
        resourceNum = resourceService.getCountByUserId(user.getId());
        // todo 可以通过pageHelper的PageInfo得到总数量
        System.out.println("resourceNum:" + resourceNum);
        return ResponseEntity.ok().body(new Response(0, "资源列表", resourceNum, resourceList));
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize, @RequestParam(value = "status", required = false, defaultValue = "-2") int status, @RequestParam(value = "order_by", required = false, defaultValue = "created_at") String orderBy) {
        List<Resource> resourceList;
        int resourceNum;
        if (status != -2) {
            resourceList = resourceService.findAllByStatus(pageIndex, pageSize, status, orderBy);
            resourceNum = resourceService.getCountByStatus(status);
        } else {
            resourceList = resourceService.findAll(pageIndex, pageSize, orderBy);
            resourceNum = resourceService.getCount();
        }
        calcResourceListAverageRate(resourceList);
        //status 0 未审核 -1 未通过审核 1 通过审核
        // todo 可以通过pageHelper的PageInfo得到总数量
        System.out.println("resourceNum:" + resourceNum);
        return ResponseEntity.ok().body(new Response(0, "资源列表", resourceNum, resourceList));
    }

    private void calcResourceListAverageRate(List<Resource> resourceList) {
        if (resourceList == null) return;
        for (Resource resource : resourceList) {
            //计算平均分数
            calcResourceAverageRate(resource);
        }
    }

    private void calcResourceAverageRate(Resource resource) {
        if (resource.getTotalRateNum() == null || resource.getTotalRateNum() == 0) {
            resource.setAverageRate(0);
        } else {
            double score = resource.getTotalRate() * 1.0 / resource.getTotalRateNum();
            resource.setAverageRate((int) Math.round(score));
        }
    }

    @RequestMapping(value = "/add")
    public String add() {
        return "uploadresource";
    }

    @GetMapping(value = "/{id}")
    public String view(@PathVariable Long id, Model model) {
        Resource resource = resourceService.findById(id);
        //计算平均分数
        calcResourceAverageRate(resource);
        ESResource esResource = new ESResource(resource);
        model.addAttribute("resource", resource);
        model.addAttribute("esResource", esResource);
        return "/resourceDetail";
    }

    @PutMapping(value = "/{id}/enable")
    public ResponseEntity<Response> enabled(@PathVariable Long id) {
        Resource resource = resourceService.findById(id);
        if (resource != null) {
            resource.setIsEnabled(false);
            resourceService.update(resource, true);
        }
        return ResponseEntity.ok().body(new Response());
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

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Resource resource) {
        resource.setUpdatedAt(new Date());
        resource.setId(id);
        resourceService.update(resource, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    //public ResponseEntity<Response> save(resource resource,BindingResult br) {
    //BindingResult 时间变成当前时间
    public ResponseEntity<Response> save(Resource resource) {
        //if (resource != null) return ResponseEntity.ok().body(new Response(0, "", 0, null));
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
        resource.setTotalRate(0L);
        resource.setTotalRateNum(0);
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
            String[] arr = resource.getFilePath().split("\\.");
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
