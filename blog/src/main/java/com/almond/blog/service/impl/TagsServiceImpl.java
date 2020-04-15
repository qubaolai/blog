package com.almond.blog.service.impl;

import com.almond.blog.mapper.*;
import com.almond.blog.mapper.myMapper.MyMapper;
import com.almond.blog.po.*;
import com.almond.blog.pojo.Result;
import com.almond.blog.service.TagsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Resource
    private TTagMapper tTagMapper;
    @Resource
    private MyMapper myMapper;
    @Resource
    private TBlogTagsMapper tBlogTagsMapper;
    @Resource
    private TBlogMapper tBlogMapper;
    @Resource
    private TUserMapper tUserMapper;
    @Resource
    private TTypeMapper tTypeMapper;

    @Override
    public List<TTag> getTags() {
        return tTagMapper.selectByExample(new TTagExample());
    }

    @Transactional
    @Override
    public Integer insertTag(TTag tTag) {
        //根据名称查标签
        TTagExample example = new TTagExample();
        TTagExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(tTag.getName());
        List<TTag> tTags = tTagMapper.selectByExample(example);
        if(tTags != null && 0 < tTags.size()){
            return 2;
        }
        int insert = tTagMapper.insert(tTag);
        return insert;
    }

    @Transactional
    @Override
    public Integer updateTag(TTag tTag) {
        return tTagMapper.updateByPrimaryKey(tTag);
    }

    @Transactional
    @Override
    public void deleteTag(Integer id) {
        tTagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TTag getTagById(Integer id) {
        return tTagMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Result> getTagTop(Integer size) {
        List<Result> tagTop = myMapper.getTagTop(size);
        //通过id获取标签
        return tagTop;
    }

    @Override
    public PageInfo<TBlog> getBlogByTagId(Integer pageNo, Integer pageSize, Integer id) {
        //通过标签id获取博客id列表
        TBlogTagsExample tBlogTagsExample = new TBlogTagsExample();
        TBlogTagsExample.Criteria criteria = tBlogTagsExample.createCriteria();
        criteria.andTagsIdEqualTo(id);
        List<TBlogTags> tBlogTags = tBlogTagsMapper.selectByExample(tBlogTagsExample);
        List<Integer> list = new ArrayList<>();
        if(tBlogTags != null && 0 < tBlogTags.size()){
            for(TBlogTags tBlogTag : tBlogTags){
                list.add(tBlogTag.getBlogsId());
            }
        }
        //通过博客id列表获取博客列表
        TBlogExample tBlogExample = new TBlogExample();
        TBlogExample.Criteria criteria1 = tBlogExample.createCriteria();
        criteria1.andIdIn(list);
        PageHelper.startPage(pageNo,pageSize);
        List<TBlog> tBlogs = tBlogMapper.selectByExample(tBlogExample);
        PageInfo<TBlog> tBlogPageInfo = new PageInfo<>(tBlogs);
        //获取作者信息
        if(tBlogPageInfo.getList() != null && 0 < tBlogPageInfo.getList().size()){
            for(TBlog tBlog : tBlogPageInfo.getList()){
                TUser tUser = tUserMapper.selectByPrimaryKey(tBlog.getUserId());
                tBlog.setTUser(tUser);
                //获取分类信息
                TType tType = tTypeMapper.selectByPrimaryKey(tBlog.getTypeId());
                tBlog.setTypeName(tType.getName());
            }
        }
        return tBlogPageInfo;
    }
}
