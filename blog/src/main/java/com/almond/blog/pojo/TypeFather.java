package com.almond.blog.pojo;

import com.almond.blog.po.TBlog;
import lombok.Data;

import java.util.List;

@Data
public class TypeFather {
    private List<TBlog> blogs;
}
