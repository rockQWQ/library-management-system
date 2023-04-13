package com.yzk.Service;

import com.yzk.Bean.User;

import java.sql.SQLException;

public interface UserService {
    User login(String username,String password)throws SQLException ;


}
