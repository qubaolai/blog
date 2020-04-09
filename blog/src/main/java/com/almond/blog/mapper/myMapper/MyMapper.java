package com.almond.blog.mapper.myMapper;

import com.almond.blog.po.TBlog;
import com.almond.blog.po.TComment;
import com.almond.blog.pojo.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyMapper {
    /**
     * 查詢分类top
     * @param size
     * @return
     */
    public List<Result> getTypeTop(Integer size);

    /**
     * 查询标签top
     * @param size
     * @return
     */
    public List<Result> getTagTop(Integer size);

    /**
     * 查询推荐博客
     * @param size
     * @return
     */
    public List<TBlog> getRecommendBlog(Integer size);

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


    List<TComment> findByBlogIdParentIdNull(@Param("blogId") Integer blogId);


    public List<TBlog> getArchiveBlog();

    public Integer countBlog();
}
