package com.yzk.Dao;

import com.yzk.Bean.User;

import java.sql.SQLException;

public interface UserDao {

    User login(String username,String password)throws SQLException;
}

