package com.almond.blog.controller;

import com.almond.blog.po.TBlog;
import com.almond.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class ArchiveController {
    @Resource
    private BlogService blogService;

    @GetMapping("/blog/archive")
    public String archive(Model model){
        Map<String, List<TBlog>> archiveBlog = blogService.getArchiveBlog();
        Integer integer = blogService.countBlog();
        model.addAttribute("count", integer);
        model.addAttribute("archive", archiveBlog);
        return "archive";
    }
}
