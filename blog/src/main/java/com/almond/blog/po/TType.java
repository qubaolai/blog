package com.almond.blog.po;

import com.almond.blog.pojo.TypeFather;

import javax.validation.constraints.NotBlank;

public class TType extends TypeFather {
    private Integer id;

    @NotBlank(message = "分类名称为空")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}