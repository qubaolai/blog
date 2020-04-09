package com.almond.blog.controller;

import com.almond.blog.po.TComment;
import com.almond.blog.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Controller
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/blog/comment/{blogId}")
    public String comments(@PathVariable Integer blogId, Model model){
        model.addAttribute("comments", commentService.getCommentsByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/blog/comment")
    public String postComment(TComment tComment){
        commentService.insertComment(tComment);
        String s = "redirect:/blog/comment/" + tComment.getBlogId();
        return s;
    }
}
