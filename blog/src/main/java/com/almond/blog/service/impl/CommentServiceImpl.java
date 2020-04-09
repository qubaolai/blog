package com.almond.blog.service.impl;

import com.almond.blog.mapper.TBlogMapper;
import com.almond.blog.mapper.TCommentMapper;
import com.almond.blog.mapper.TUserMapper;
import com.almond.blog.mapper.myMapper.MyMapper;
import com.almond.blog.po.TBlog;
import com.almond.blog.po.TComment;
import com.almond.blog.po.TCommentExample;
import com.almond.blog.po.TUser;
import com.almond.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private TCommentMapper tCommentMapper;
    @Resource
    private MyMapper myMapper;
    @Resource
    private HttpSession httpSession;
    @Resource
    private TBlogMapper tBlogMapper;
    @Resource
    private TUserMapper tUserMapper;

    @Transactional
    @Override
    public Integer insertComment(TComment tComment) {
        TUser user = (TUser) httpSession.getAttribute("user");
        if(user != null && tComment.getNickname().equals(user.getNickname())){
            //该评论为博主提交
            tComment.setAvatar(user.getAvatar());
            //0代表管理员
            tComment.setAdminComment(0);
        }else{
            tComment.setAvatar("https://picsum.photos/id/1021/100/100");
        }
        if(tComment.getParentCommentId() == -1){
            //判断是否有父级评论id
            //不存在则为回复 设置父级id为null
            tComment.setParentCommentId(null);
        }
        tComment.setCreateTime(new Date());
        tCommentMapper.insert(tComment);
        return 0;
    }
    @Override
    public List<TComment> getCommentsByBlogId(Integer blogId) {
        //没有父节点的默认为-1
        List<TComment> comments = myMapper.findByBlogIdParentIdNull(blogId);
        for(TComment tComment : comments){
            TCommentExample example = new TCommentExample();
            TCommentExample.Criteria criteria = example.createCriteria();
            criteria.andParentCommentIdEqualTo(tComment.getId());
            List<TComment> list = tCommentMapper.selectByExample(example);
            tComment.setReplyComments(list);
        }
//        List<TComment> list = eachComment(comments);
        combineChildren(comments);
        //父级评论
        for(TComment tComment : comments){
            if(tComment.getReplyComments() != null){
                for(TComment comment : tComment.getReplyComments()){
                    TCommentExample example = new TCommentExample();
                    TCommentExample.Criteria criteria = example.createCriteria();
                    criteria.andIdEqualTo(comment.getParentCommentId());
                    List<TComment> tComments = tCommentMapper.selectByExample(example);
                    comment.setFatherComment(tComments.get(0));
                }
            }
        }
        return comments;
    }
    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<TComment> eachComment(List<TComment> comments) {
        List<TComment> commentsView = new ArrayList<>();
        for (TComment comment : comments) {
            TComment c = new TComment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<TComment> comments) {

        for (TComment comment : comments) {
            TCommentExample example = new TCommentExample();
            TCommentExample.Criteria criteria = example.createCriteria();
            criteria.andParentCommentIdEqualTo(comment.getId());
            List<TComment> list = tCommentMapper.selectByExample(example);
            comment.setReplyComments(list);
            List<TComment> replys1 = comment.getReplyComments();
            for(TComment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    //存放迭代找出的所有子代的集合
    private List<TComment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(TComment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        TCommentExample example = new TCommentExample();
        TCommentExample.Criteria criteria = example.createCriteria();
        criteria.andParentCommentIdEqualTo(comment.getId());
        List<TComment> list = tCommentMapper.selectByExample(example);
        comment.setReplyComments(list);
        if (comment.getReplyComments().size()>0) {
            List<TComment> replys = comment.getReplyComments();
            for (TComment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
