package com.deep.blog.dao.user;

import com.deep.blog.entity.User;

public interface UserDao {

    void saveUser(User user);
    User findByUsername(String username);
}
