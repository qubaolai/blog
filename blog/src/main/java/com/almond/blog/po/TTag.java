package com.almond.blog.po;

import javax.validation.constraints.NotBlank;

public class TTag {
    private Integer id;

    @NotBlank(message = "标签名称不能为空!")
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