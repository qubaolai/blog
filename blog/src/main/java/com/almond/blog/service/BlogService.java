package com.almond.blog.service;

import com.almond.blog.po.TBlog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    /**
     * 条件查询博客
     * @param tBlog
     * @return
     */
    public List<TBlog> getBlogs(TBlog tBlog);

    /**
     * 添加博客
     * @param tBlog
     * @return
     */
    public Integer insertBlog(TBlog tBlog,String tagIds);

    /**
     * 修改博客
     * @param tBlog
     * @return
     */
    public Integer updateBlog(TBlog tBlog);

    /**
     * 通过id获取博客
     * @param id
     * @return
     */
    public TBlog getBlogById(Integer id);

    /**
     * 通过id删除博客
     * @param id
     */
    public void deleteBlog(Integer id);

    /**
     * 获取所有文章
     * @return
     */
    public List<TBlog> allBlog();

    /**
     * 最新推荐博客
     * @return
     */
    public List<TBlog> recommendBlog();

    /**
     * 条件查询博客
     * @param search
     * @return
     */
    public List<TBlog> searchBlog(String search);

    /**
     * 博客详情
     * @param id
     * @return
     */
    public TBlog blogDetails(Integer id);

    /**
     * 通过分类id获取博客列表
     * @param id
     * @return
     */
    public List<TBlog> getBlogByTypeId(Integer id);

    /**
     * 各年份博客信息
     * @return
     */
    public Map<String, List<TBlog>> getArchiveBlog();

    /**
     * 博客总数
     * @return
     */
    public Integer countBlog();
}
