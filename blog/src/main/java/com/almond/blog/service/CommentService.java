package com.almond.blog.service;

import com.almond.blog.po.TComment;

import java.util.List;

public interface CommentService {

    /**
     * 通过博客id获取评论
     * @param blogId
     * @return
     */
    public List<TComment> getCommentsByBlogId(Integer blogId);

    /**
     * 添加评论
     * @param tComment
     * @return
     */
    public Integer insertComment(TComment tComment);
}
