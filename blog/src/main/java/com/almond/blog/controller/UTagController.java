package com.almond.blog.controller;

import com.almond.blog.pojo.Result;
import com.almond.blog.service.TagsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UTagController {

    @Resource
    private TagsService tagsService;

    @GetMapping("/blog/tags/{id}")
    public String toTypePage(@PathVariable Integer id, Model model){
        //获取所有分类信息
        List<Result> tagTop = tagsService.getTagTop(0);
        model.addAttribute("TagResult", tagTop);
        //通过标签id获取博客列表
        if(id == 0){
            id = tagTop.get(0).getId();
        }
        model.addAttribute("blogs", tagsService.getBlogByTagId(id));
        model.addAttribute("activeId", id);
        return "labels";
    }
}
