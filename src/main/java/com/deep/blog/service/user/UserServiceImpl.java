package com.deep.blog.service.user;

import com.deep.blog.dao.user.UserDao;
import com.deep.blog.dao.user.UserDaoImpl;
import com.deep.blog.entity.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {
    private static final UserDao userDao = new UserDaoImpl();


    @Override
    public User login(String username, String rawPassword) {

        User user = userDao.findByUsername(username);
        if (user == null) {
            return null; // username doesnt exist
        }

        boolean matches = BCrypt.checkpw(rawPassword, user.getPasswordHash());
        return matches ? user : null; // here null for the incorrect password
    }

    @Override
    public void register(String username, String email, String rawPassword) {

        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(hashed);

        userDao.saveUser(user);
    }

}
