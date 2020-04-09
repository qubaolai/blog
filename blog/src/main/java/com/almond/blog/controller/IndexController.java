package com.almond.blog.controller;

import com.almond.blog.po.TBlog;
import com.almond.blog.service.BlogService;
import com.almond.blog.service.TagsService;
import com.almond.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagsService tagsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("blogs", blogService.allBlog());
        model.addAttribute("TypeResult", typeService.getTypeTop(7));
        model.addAttribute("tagResult", tagsService.getTagTop(7));
        model.addAttribute("recommendBlog", blogService.recommendBlog());
        return "index";
    }

    @PostMapping("/blog/search")
    public String search(Model model, @RequestParam String search){
        List<TBlog> tBlogs = blogService.searchBlog(search);
        model.addAttribute("blogs", tBlogs);
        return "search";
    }

    @GetMapping("/blog/details/{id}")
    public String blogDetails(@PathVariable Integer id, Model model){
        TBlog tBlog = blogService.blogDetails(id);
        model.addAttribute("blog", tBlog);
        return "blog";
    }

    @GetMapping("/blog/about")
    public String about(){
        return "about";
    }
}
