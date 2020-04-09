package com.almond.blog.controller.admin;

import com.almond.blog.po.TBlog;
import com.almond.blog.po.TTag;
import com.almond.blog.po.TType;
import com.almond.blog.po.TUser;
import com.almond.blog.service.BlogService;
import com.almond.blog.service.TagsService;
import com.almond.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private TypeService typeService;
    @Resource
    private BlogService blogService;
    @Resource
    private TagsService tagsService;


    @GetMapping("/blogs")
    public String getBlogs(Model model){
        List<TType> types = typeService.getTypes();
        List<TBlog> blogs = blogService.getBlogs(new TBlog());
        model.addAttribute("types", types);
        model.addAttribute("blogs", blogs);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String searchBlog(Model model,TBlog tBlog){
        List<TType> types = typeService.getTypes();
        List<TBlog> blogs = blogService.getBlogs(tBlog);
        model.addAttribute("types", types);
        model.addAttribute("blogs", blogs);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/inputPage")
    public String inputPage(Model model){
        List<TType> types = typeService.getTypes();
        List<TTag> tags = tagsService.getTags();
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("TBlog", new TBlog());
        return "admin/blogs-input";
    }

    @PostMapping("/blogs/insert")
    public String insertBlog(TBlog tBlog, HttpSession session, RedirectAttributes attributes){
        TUser user = (TUser) session.getAttribute("user");
        tBlog.setUserId(user.getId());
        Integer integer = blogService.insertBlog(tBlog,tBlog.getTagIds());
        if(integer != 0){
            attributes.addFlashAttribute("message", "操作成功!");
        }else{
            attributes.addFlashAttribute("message", "操作失败!");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/updatePage/{id}")
    public String updatePage(@PathVariable Integer id, Model model){
        List<TType> types = typeService.getTypes();
        List<TTag> tags = tagsService.getTags();
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        TBlog blogById = blogService.getBlogById(id);
        model.addAttribute("TBlog", blogById);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable Integer id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "添加成功!");
        return "redirect:/admin/blogs";
    }
}
