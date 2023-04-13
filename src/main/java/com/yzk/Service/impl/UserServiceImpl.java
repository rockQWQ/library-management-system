package com.yzk.Service.impl;

import com.yzk.Bean.User;
import com.yzk.Dao.impl.UserDaoImpl;
import com.yzk.Service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    UserDaoImpl userdao = new UserDaoImpl();
    @Override
    public User login(String username,String password)throws SQLException {
        return userdao.login(username, password);
    }

}
