package com.almond.blog.service.impl;

import com.almond.blog.commons.utils.MarkdownUtils;
import com.almond.blog.mapper.*;
import com.almond.blog.mapper.myMapper.MyMapper;
import com.almond.blog.po.*;
import com.almond.blog.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private TBlogMapper tBlogMapper;
    @Resource
    private TTypeMapper tTypeMapper;
    @Resource
    private TBlogTagsMapper tBlogTagsMapper;
    @Resource
    private TUserMapper tUserMapper;
    @Resource
    private MyMapper myMapper;
    @Resource
    private TCommentMapper tCommentMapper;
    @Resource
    private TTagMapper tTagMapper;

    @Override
    public List<TBlog> getBlogs(TBlog tBlog) {
        TBlogExample tBlogExample = new TBlogExample();
        TBlogExample.Criteria criteria = tBlogExample.createCriteria();
        if (!StringUtils.isBlank(tBlog.getTitle())) {
            criteria.andTitleLike("%" + tBlog.getTitle() + "%");
        }
        if (tBlog.getTypeId() != null) {
            criteria.andTypeIdEqualTo(tBlog.getTypeId());
        }
        //1为推荐
        if (tBlog.getRecommend() != null && tBlog.getRecommend() == 1) {
            criteria.andRecommendEqualTo(1);
        }
        List<TBlog> tBlogs = tBlogMapper.selectByExample(tBlogExample);
        if (tBlogs != null) {
            for (TBlog tBlog1 : tBlogs) {
                TType tType = tTypeMapper.selectByPrimaryKey(tBlog1.getTypeId());
                tBlog1.setTType(tType);
            }
        }
        return tBlogs;
    }

    @Transactional
    @Override
    public Integer insertBlog(TBlog tBlog, String tagIds) {
        if (tBlog.getId() == null) {
            tBlog.setCreateTime(new Date());
            tBlog.setUpdateTime(new Date());
            tBlog.setViews(0);
            tBlogMapper.insertSelective(tBlog);
            int id = tBlog.getId();
            //将string类型id转为list
            List<Integer> ids = new ArrayList<>();
            if (!StringUtils.isBlank(tagIds)) {
                String[] split = tagIds.split(",");
                for (String str : split) {
                    ids.add(Integer.parseInt(str));
                }
            }
            //循环将标签id插入到blog-tag表
            for (Integer integer : ids) {
                TBlogTags tBlogTags = new TBlogTags();
                tBlogTags.setBlogsId(id);
                tBlogTags.setTagsId(integer);
                tBlogTagsMapper.insert(tBlogTags);
            }
            return id;
        } else {
            //将string类型id转为list
            String[] split = tagIds.split(",");
            List<Integer> ids = new ArrayList<>();
            if (!StringUtils.isBlank(tagIds)) {
                for (String str : split) {
                    ids.add(Integer.parseInt(str));
                }
            }
            TBlogTagsExample example = new TBlogTagsExample();
            TBlogTagsExample.Criteria criteria = example.createCriteria();
            criteria.andBlogsIdEqualTo(tBlog.getId());
            tBlogTagsMapper.deleteByExample(example);
            //循环将标签id插入到blog-tag表
            for (Integer integer : ids) {
                TBlogTags tBlogTags = new TBlogTags();
                tBlogTags.setBlogsId(tBlog.getId());
                tBlogTags.setTagsId(integer);
                tBlogTagsMapper.insert(tBlogTags);
            }
            tBlog.setUpdateTime(new Date());
            return tBlogMapper.updateByPrimaryKeySelective(tBlog);
        }
    }

    @Transactional
    @Override
    public Integer updateBlog(TBlog tBlog) {
        return tBlogMapper.updateByPrimaryKey(tBlog);
    }

    @Override
    public TBlog getBlogById(Integer id) {
        TBlog tBlog = tBlogMapper.selectByPrimaryKey(id);
        TBlogTagsExample example = new TBlogTagsExample();
        TBlogTagsExample.Criteria criteria = example.createCriteria();
        criteria.andBlogsIdEqualTo(id);
        List<TBlogTags> tBlogTags = tBlogTagsMapper.selectByExample(example);
        StringBuffer tarIds = new StringBuffer();
        if (tBlogTags != null && 0 < tBlogTags.size()) {
            for (TBlogTags tBlogTags1 : tBlogTags) {
                tarIds.append(tBlogTags1.getTagsId());
                tarIds.append(",");
            }
            String s = tarIds.toString();
            tBlog.setTagIds(s.substring(0, s.length() - 1));
        }
        return tBlog;
    }

    @Override
    public void deleteBlog(Integer id) {
        TBlogTagsExample example = new TBlogTagsExample();
        TBlogTagsExample.Criteria criteria = example.createCriteria();
        criteria.andBlogsIdEqualTo(id);
        tBlogTagsMapper.deleteByExample(example);
        //通过博客id获取评论id
        TCommentExample example1 = new TCommentExample();
        TCommentExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andBlogIdEqualTo(id);
        List<TComment> tComments = tCommentMapper.selectByExample(example1);
        for (TComment tComment : tComments) {
            deleteComment(tComment.getId());
        }
        tBlogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论id
     */
    public void deleteComment(Integer commentId) {
        tCommentMapper.deleteByPrimaryKey(commentId);
        //查询父评论id为commentId的评论 删除
        TCommentExample example = new TCommentExample();
        TCommentExample.Criteria criteria = example.createCriteria();
        criteria.andParentCommentIdEqualTo(commentId);
        List<TComment> tComments = tCommentMapper.selectByExample(example);
        if (tComments != null && tComments.size() > 0) {
            for (TComment tComment : tComments) {
                deleteComment(tComment.getId());
            }
        }
    }

    @Override
    public List<TBlog> allBlog() {
        List<TBlog> tBlogs = tBlogMapper.selectByExample(new TBlogExample());
        if (tBlogs != null && 0 < tBlogs.size()) {
            for (TBlog tBlog : tBlogs) {
                //根据博客id查询分类
                TType tType = tTypeMapper.selectByPrimaryKey(tBlog.getTypeId());
                tBlog.setTType(tType);
                //设置博客作者
                TUser tUser = tUserMapper.selectByPrimaryKey(tBlog.getUserId());
                tBlog.setTUser(tUser);
            }
        }
        return tBlogs;
    }

    @Override
    public List<TBlog> recommendBlog() {
        List<TBlog> recommendBlog = myMapper.getRecommendBlog(10);
        return recommendBlog;
    }

    @Override
    public List<TBlog> searchBlog(String search) {
        List<TBlog> tBlogs = myMapper.searchBlog(search);
        return tBlogs;
    }

    @Transactional
    @Override
    public TBlog blogDetails(Integer id) {
        TBlog tBlog = myMapper.blogDetails(id);
        tBlog.setViews(tBlog.getViews() + 1);
        tBlogMapper.updateByPrimaryKey(tBlog);
        String content = tBlog.getContent();
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        tBlog.setContent(s);
        return tBlog;
    }

    @Override
    public List<TBlog> getBlogByTypeId(Integer id) {
        TBlogExample tBlogExample = new TBlogExample();
        TBlogExample.Criteria criteria = tBlogExample.createCriteria();
        criteria.andTypeIdEqualTo(id);
        List<TBlog> tBlogs = tBlogMapper.selectByExample(tBlogExample);
        //获取博客的标签
        for (TBlog tBlog : tBlogs) {
            TBlogTagsExample tBlogTagsExample = new TBlogTagsExample();
            TBlogTagsExample.Criteria criteria1 = tBlogTagsExample.createCriteria();
            criteria1.andBlogsIdEqualTo(tBlog.getId());
            List<TBlogTags> tBlogTags = tBlogTagsMapper.selectByExample(tBlogTagsExample);
            if (tBlogTags != null && 0 < tBlogTags.size()) {
                List<Integer> tagIds = new ArrayList<>();
                for (TBlogTags tTags : tBlogTags) {
                    tagIds.add(tTags.getTagsId());
                }
                TTagExample tTagExample = new TTagExample();
                TTagExample.Criteria tagCriteria = tTagExample.createCriteria();
                tagCriteria.andIdIn(tagIds);
                List<TTag> tTags = tTagMapper.selectByExample(tTagExample);
                tBlog.setTags(tTags);
            }
            //获取作者信息
            TUser tUser = tUserMapper.selectByPrimaryKey(tBlog.getUserId());
            tBlog.setTUser(tUser);
            //获取分类信息
            TType tType = tTypeMapper.selectByPrimaryKey(tBlog.getTypeId());
            tBlog.setTypeName(tType.getName());
        }
        return tBlogs;
    }

    @Override
    public Map<String, List<TBlog>> getArchiveBlog() {
        List<TBlog> list = myMapper.getArchiveBlog();
        Map<String, List<TBlog>> map = new HashMap<>();
        for (TBlog num : list){
            // map是否包含此key，若已经包含则添加一个新的对象到对应value集合中
            if (map.containsKey(num.getUpdateYear())){
                map.get(num.getUpdateYear()).add(num);
            }else{
                // map不包含此key，则重新创建一个新集合，并把这个对象添加进集合
                // ，再把集合放到map中
                List<TBlog> newList = new ArrayList<>();
                newList.add(num);
                map.put(num.getUpdateYear(), newList);
            }
        }
        return map;
    }

    @Override
    public Integer countBlog() {
        return myMapper.countBlog();
    }


}
