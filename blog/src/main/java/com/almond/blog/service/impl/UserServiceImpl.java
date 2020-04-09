package com.almond.blog.service.impl;

import com.almond.blog.commons.utils.MD5Utils;
import com.almond.blog.mapper.TUserMapper;
import com.almond.blog.po.TUser;
import com.almond.blog.po.TUserExample;
import com.almond.blog.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private TUserMapper tUserMapper;

    @Override
    public TUser getUserByUserName(String username, String password) {
        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(MD5Utils.code(password));
        List<TUser> tUsers = tUserMapper.selectByExample(example);
        if(tUsers == null || tUsers.size() <= 0){
            return null;
        }
        return tUsers.get(0);
    }
}
