package com.almond.blog.service.impl;

import com.almond.blog.mapper.TBlogMapper;
import com.almond.blog.mapper.TTypeMapper;
import com.almond.blog.mapper.myMapper.MyMapper;
import com.almond.blog.po.TBlog;
import com.almond.blog.po.TBlogExample;
import com.almond.blog.po.TType;
import com.almond.blog.po.TTypeExample;
import com.almond.blog.pojo.Result;
import com.almond.blog.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Resource
    private TTypeMapper tTypeMapper;
    @Resource
    private MyMapper myMapper;
    @Resource
    private TBlogMapper tBlogMapper;

    /**
     * 添加
     * @param tType
     */
    @Transactional
    @Override
    public Integer insertType(TType tType) {
        //根据名称查询分类
        TTypeExample tTypeExample = new TTypeExample();
        TTypeExample.Criteria criteria = tTypeExample.createCriteria();
        criteria.andNameEqualTo(tType.getName());
        List<TType> tTypes = tTypeMapper.selectByExample(tTypeExample);
        if(tTypes != null && 0 < tTypes.size()){
            //2代表该分类存在
            return 2;
        }
        int insert = tTypeMapper.insert(tType);
        return insert;
    }

    /**
     * 查询
     * @return
     */
    @Override
    public List<TType> getTypes() {
        return tTypeMapper.selectByExample(new TTypeExample());
    }

    /**
     * 修改
     * @param tType
     */
    @Transactional
    @Override
    public Integer updateType(TType tType) {
        int update = tTypeMapper.updateByPrimaryKey(tType);
        return update;
    }

    /**
     * 删除
     * @param id
     */
    @Transactional
    @Override
    public void deleteType(Integer id) {
        tTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TType getTypeById(Integer id) {
        TType tType = tTypeMapper.selectByPrimaryKey(id);
        return tType;
    }

    @Override
    public List<Result> getTypeTop(Integer size) {

        List<Result> typeTop = myMapper.getTypeTop(size);
        return typeTop;
    }

}
