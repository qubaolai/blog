package com.almond.blog.controller.admin;

import com.almond.blog.po.TTag;
import com.almond.blog.service.TagsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagsService tagsService;

    /**
     * 获取所有标签
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(Model model){
        List<TTag> tags = tagsService.getTags();
        model.addAttribute("tags", tags);
        return "admin/tags";
    }

    /**
     * 跳转到新增页面
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String inputPage(Model model){
        model.addAttribute("TTag", new TTag());
        return "admin/tags-input";
    }

    /**
     * 添加标签
     * @param tTag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/insertTag")
    public String insertTag(@Validated TTag tTag, BindingResult result, RedirectAttributes attributes){
        Integer integer = tagsService.insertTag(tTag);
        if(integer == 2){
            //标签名称重复
            result.rejectValue("name", "TagError", "不允许添加重复标签!");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        if(integer == 1){
            attributes.addFlashAttribute("message", "添加成功!");
        }else{
            attributes.addFlashAttribute("message", "添加失败!");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/input/{id}")
    public String updatePage(@PathVariable Integer id, Model model){
        TTag tagById = tagsService.getTagById(id);
        model.addAttribute("TTag", tagById);
        return "admin/tags-input";
    }

    /**
     * 修改标签
     * @param tTag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags/edit")
    public String editTag(@Validated TTag tTag, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Integer integer = tagsService.updateTag(tTag);
        if(integer == 1){
            attributes.addFlashAttribute("message", "修改成功!");
        }else{
            attributes.addFlashAttribute("message", "修改失败!");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete/{id}")
    public String deleteTag(@PathVariable Integer id, RedirectAttributes attributes){
        tagsService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功!");
        return "redirect:/admin/tags";
    }
}
