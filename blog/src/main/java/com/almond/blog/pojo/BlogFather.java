package com.almond.blog.pojo;

import com.almond.blog.po.TTag;
import com.almond.blog.po.TType;
import com.almond.blog.po.TUser;
import lombok.Data;

import java.util.List;

@Data
public class BlogFather {
    private TType tType;

    private String tagIds;

    private List<TTag> tags;

    private TUser tUser;

    private String typeName;

    private Object content;

    private String updateYear;
}
