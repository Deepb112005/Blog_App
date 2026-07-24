package com.deep.blog.service.user;

import com.deep.blog.entity.User;

public interface UserService {
    void register(String username, String email, String rawPassword);

    User login(String username, String rawPassword);


}
