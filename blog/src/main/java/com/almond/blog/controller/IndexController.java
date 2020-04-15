package com.almond.blog.controller;

import com.almond.blog.commons.exception.exceptions.NoDataException;
import com.almond.blog.po.TBlog;
import com.almond.blog.service.BlogService;
import com.almond.blog.service.TagsService;
import com.almond.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@SessionAttributes("recommendBlog")
@Controller
public class IndexController {

    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagsService tagsService;

    /**
     * 博客主页
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="2")int pageSize,Model model){
        model.addAttribute("page", blogService.allBlog(pageNo,pageSize));
        model.addAttribute("TypeResult", typeService.getTypeTop(7));
        model.addAttribute("tagResult", tagsService.getTagTop(7));
        model.addAttribute("recommendBlog", blogService.recommendBlog());
        return "index";
    }

    /**
     * 搜索博客
     * @param model
     * @param search
     * @return
     */
    @GetMapping("/blog/search")
    public String search(Model model, @RequestParam String search,@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="2")int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TBlog> tBlogs = blogService.searchBlog(search);
        PageInfo<TBlog> tBlogPageInfo = new PageInfo<>(tBlogs);
        model.addAttribute("page", tBlogPageInfo);
        model.addAttribute("search", search);
        return "search";
    }

    /**
     * 博客详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/details/{id}")
    public String blogDetails(@PathVariable Integer id, Model model){
        TBlog tBlog = blogService.blogDetails(id);
        model.addAttribute("blog", tBlog);
        return "blog";
    }

    /**
     * 关于我
     * @return
     */
    @GetMapping("/blog/about")
    public String about(){
        return "about";
    }
}
