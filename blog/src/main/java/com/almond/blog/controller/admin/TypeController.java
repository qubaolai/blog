package com.almond.blog.controller.admin;

import com.almond.blog.po.TType;
import com.almond.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Resource
    private TypeService typeService;

    /**
     * 分类列表
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String typeList(Model model){
        List<TType> types = typeService.getTypes();
        model.addAttribute("types", types);
        return "admin/types";
    }

    /**
     * 跳转到新增页
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("TType", new TType());
        return "admin/types-input";
    }

    /**
     * 添加分类信息
     * @param tType
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/insertType")
    public String insertType(@Valid TType tType, BindingResult result, RedirectAttributes attributes){
        Integer integer = typeService.insertType(tType);
        if(integer == 2){
            //name必须与实体中的name属性一致
            result.rejectValue("name", "typeError", "不允许添加重复分类!");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        if(integer == 1){
            //提交成功
            attributes.addFlashAttribute("message", "添加成功!");
        }else{
            attributes.addFlashAttribute("message", "添加失败!");
        }
        return  "redirect:/admin/types";
    }

    //获取被修改的分类信息
    @GetMapping("/types/input/{id}")
    public String update(@PathVariable Integer id, Model model){
        TType typeById = typeService.getTypeById(id);
        model.addAttribute("TType", typeById);
        return "admin/types-input";
    }

    @PostMapping("/types/edit")
    public String editType(@Valid TType tType, BindingResult result,RedirectAttributes attributes){
        Integer integer = typeService.updateType(tType);
        if(result.hasErrors()){
            return "admin/types-input";
        }
        if(integer == 1){
            //提交成功
            attributes.addFlashAttribute("message", "修改成功!");
        }else{
            attributes.addFlashAttribute("message", "修改失败!");
        }
        return  "redirect:/admin/types";
    }

    @GetMapping("/types/delete/{id}")
    public String deleteType(@PathVariable Integer id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功!");
        return  "redirect:/admin/types";
    }
}
