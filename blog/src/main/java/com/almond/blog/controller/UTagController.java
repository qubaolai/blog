package com.almond.blog.controller;

import com.almond.blog.po.TBlog;
import com.almond.blog.pojo.Result;
import com.almond.blog.service.TagsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UTagController {

    @Resource
    private TagsService tagsService;

    @GetMapping("/blog/tags/{id}")
    public String toTypePage(@PathVariable Integer id,@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="5")int pageSize, Model model){
        //获取所有分类信息
        List<Result> tagTop = tagsService.getTagTop(0);
        model.addAttribute("TagResult", tagTop);
        //通过标签id获取博客列表
        if(id == 0){
            id = tagTop.get(0).getId();
        }
        PageInfo<TBlog> blogByTagId = tagsService.getBlogByTagId(pageNo, pageSize, id);
        model.addAttribute("page", blogByTagId);
        model.addAttribute("activeId", id);
        return "labels";
    }
}
