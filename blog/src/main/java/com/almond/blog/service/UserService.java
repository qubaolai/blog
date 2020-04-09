package com.almond.blog.service;

import com.almond.blog.po.TUser;

public interface UserService {
    /**
     * 通过用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    public TUser getUserByUserName(String username, String password);
}
