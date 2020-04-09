package com.almond.blog.mapper;

import com.almond.blog.po.TBlog;
import com.almond.blog.po.TBlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBlogMapper {
    int countByExample(TBlogExample example);

    int deleteByExample(TBlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TBlog record);

    int insertSelective(TBlog record);

    List<TBlog> selectByExampleWithBLOBs(TBlogExample example);

    List<TBlog> selectByExample(TBlogExample example);

    TBlog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TBlog record, @Param("example") TBlogExample example);

    int updateByExampleWithBLOBs(@Param("record") TBlog record, @Param("example") TBlogExample example);

    int updateByExample(@Param("record") TBlog record, @Param("example") TBlogExample example);

    int updateByPrimaryKeySelective(TBlog record);

    int updateByPrimaryKeyWithBLOBs(TBlog record);

    int updateByPrimaryKey(TBlog record);
}