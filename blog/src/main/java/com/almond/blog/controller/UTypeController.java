package com.almond.blog.controller;

import com.almond.blog.pojo.Result;
import com.almond.blog.service.BlogService;
import com.almond.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UTypeController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;

    /**
     * 跳转到分类页面
     * @return model
     */
    @GetMapping("/blog/types/{id}")
    public String toTypePage(@PathVariable Integer id, Model model){
        //获取所有分类信息
        List<Result> typeTop = typeService.getTypeTop(0);
        model.addAttribute("TypeResult", typeTop);
        //通过分类id获取博客列表
        if(id == 0){
            id = typeTop.get(0).getId();
        }
        model.addAttribute("blogs", blogService.getBlogByTypeId(id));
        model.addAttribute("activeId", id);
        return "types";
    }
}
