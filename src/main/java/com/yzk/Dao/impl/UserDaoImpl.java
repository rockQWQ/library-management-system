package com.yzk.Dao.impl;

import com.yzk.Bean.User;
import com.yzk.Dao.UserDao;
import com.yzk.jdbcUtil.JdbcUtil;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(String username,String password)throws SQLException {
        String sql = "select username,password from user where userName=? and password=?";

        List<User> query = JdbcUtil.query(sql, new Object[]{username, password}, User.class);
        if(query.size()>=0){
            return query.get(0);
        }
        return null;
    }

}
