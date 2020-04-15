package com.almond.blog.service;

import com.almond.blog.po.TBlog;
import com.almond.blog.po.TTag;
import com.almond.blog.pojo.Result;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TagsService {
    /**
     * 获取所有标签
     * @return
     */
    public List<TTag> getTags();

    /**
     * 添加标签
     * @param tTag
     * @return
     */
    public Integer insertTag(TTag tTag);

    /**
     * 修改标签
     * @param tTag
     * @return
     */
    public Integer updateTag(TTag tTag);

    /**
     * 删除标签
     * @param id
     */
    public void deleteTag(Integer id);

    /**
     * 通过id获取标签
     * @param id
     * @return
     */
    public TTag getTagById(Integer id);

    /**
     * 获取标签top
     * @param size
     * @return
     */
    public List<Result> getTagTop(Integer size);

    /**
     * 通过标签id获取博客列表
     * @param id
     * @return
     */
    public PageInfo<TBlog> getBlogByTagId(Integer pageNo, Integer pageSize, Integer id);
}
