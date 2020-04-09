package com.almond.blog.pojo;

import com.almond.blog.po.TComment;
import com.almond.blog.po.TUser;
import lombok.Data;

@Data
public class CommentFather {
    private TComment fatherComment;

    private TUser tUser;
}
