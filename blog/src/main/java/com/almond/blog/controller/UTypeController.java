package com.almond.blog.controller;

import com.almond.blog.po.TBlog;
import com.almond.blog.pojo.Result;
import com.almond.blog.service.BlogService;
import com.almond.blog.service.TypeService;
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
    public String toTypePage(@PathVariable Integer id, @RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="2")int pageSize, Model model){
        //获取所有分类信息
        List<Result> typeTop = typeService.getTypeTop(0);
        model.addAttribute("TypeResult", typeTop);
        //通过分类id获取博客列表
        if(id == 0){
            id = typeTop.get(0).getId();
        }
        PageHelper.startPage(pageNo,pageSize);
        List<TBlog> blogByTypeId = blogService.getBlogByTypeId(id);
        PageInfo<TBlog> tBlogPageInfo = new PageInfo<>(blogByTypeId);
        model.addAttribute("page", tBlogPageInfo);
        model.addAttribute("activeId", id);
        return "types";
    }
}
