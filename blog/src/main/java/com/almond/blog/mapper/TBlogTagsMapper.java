package com.almond.blog.mapper;

import com.almond.blog.po.TBlogTags;
import com.almond.blog.po.TBlogTagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBlogTagsMapper {
    int countByExample(TBlogTagsExample example);

    int deleteByExample(TBlogTagsExample example);

    int insert(TBlogTags record);

    int insertSelective(TBlogTags record);

    List<TBlogTags> selectByExample(TBlogTagsExample example);

    int updateByExampleSelective(@Param("record") TBlogTags record, @Param("example") TBlogTagsExample example);

    int updateByExample(@Param("record") TBlogTags record, @Param("example") TBlogTagsExample example);
}