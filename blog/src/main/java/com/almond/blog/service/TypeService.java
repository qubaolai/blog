package com.almond.blog.service;

import com.almond.blog.po.TType;
import com.almond.blog.pojo.Result;

import java.util.List;

public interface TypeService {
    /**
     * 添加
     * @param tType
     */
    public Integer insertType(TType tType);

    /**
     * 查询
     * @return
     */
    public List<TType> getTypes();

    /**
     * 修改
     * @param tType
     */
    public Integer updateType(TType tType);

    /**
     * 删除
     * @param id
     */
    public void deleteType(Integer id);

    /**
     * 通过id查询分类
     * @param id
     * @return
     */
    public TType getTypeById(Integer id);

    /**
     * 根据文章数量获取前七个分类
     * @return
     */
    public List<Result> getTypeTop(Integer size);

}
