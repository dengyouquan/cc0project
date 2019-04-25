package com.dyq.demo.controller.api;

import com.dyq.demo.model.Comment;
import com.dyq.demo.model.Resource;
import com.dyq.demo.model.ResourceStatus;
import com.dyq.demo.model.User;
import com.dyq.demo.service.CommentService;
import com.dyq.demo.service.ResourceService;
import com.dyq.demo.service.UserService;
import com.dyq.demo.util.FastDFSClientWrapper;
import com.dyq.demo.vo.Response;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public ResponseEntity<Response> getAllByUser(@RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize) {
        User user = userService.getUserByPrincipal();
        List<Comment> commentList = commentService.findAllByUserId(pageIndex, pageSize, user.getId());
        int commentNum = commentService.countByUserId(user.getId());
        // todo 可以通过pageHelper的PageInfo得到总数量
        System.out.println("resourceNum:" + commentNum);
        return ResponseEntity.ok().body(new Response(0, "评论列表", commentNum, commentList));
    }

    @GetMapping
    public String comment(Model model,
                          @RequestParam(value = "async", required = false) boolean async,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
                          @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize,
                          @RequestParam(value = "d_id", required = false, defaultValue = "0") Long d_id,
                          @ModelAttribute("d_id") Long red_id) {
        Long id = d_id == 0 ? red_id : d_id;
        System.out.println("d_id:" + d_id + ",red_id:" + red_id + ",id:" + id);
        List<Comment> comments = null;
        comments = commentService.findAllByResourceId(id, pageIndex - 1, pageSize, "created_at desc");
        long commentCount = commentService.countByResourceId(id);
        model.addAttribute("resourceComments", comments);
        System.out.println("resourceComments:" + comments);
        model.addAttribute("commentCount", commentCount);
        System.out.println("commentCount:" + commentCount);
        //System.out.println(async?"redirect:/documentdetail?did="+id+" :: #commentContainerSysc":"redirect:/documentdetail?did="+id);
        System.out.println(async ? "/fragments/comment::#commentContainerSysc" : "/fragments/comment");
        //return (async?"redirect:/documentdetail?did="+id+"::#commentContainerSysc":"redirect:/documentdetail?did="+id);
        return (async ? "/fragments/comment::#commentContainerSysc" : "/fragments/comment");

    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Response> save(@RequestParam(name = "content") String content,
                                         @RequestParam(name = "r_id") Long resourceId) {
        Comment comment = new Comment();
        User principal = userService.getUserByPrincipal();
        comment.setResourceId(resourceId);
        comment.setUserId(principal.getId());
        comment.setContent(content);
        comment.setFromAvatar(principal.getAvatar());
        comment.setFromName(principal.getUsername());
        comment.setEnabled(true);
        commentService.save(comment);
        return ResponseEntity.ok().body(new Response(0, "新增成功", 0, null));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        commentService.remove(id);
        return ResponseEntity.ok().body(new Response(0, "删除成功", 0, null));
    }
}
