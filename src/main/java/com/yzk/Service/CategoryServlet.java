package com.yzk.Service;

import com.yzk.Bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryServlet {
    List<Category> queryList() throws SQLException;

    int insert(Category category)throws SQLException;

    int delete(int id) throws SQLException;

    int update(Category id)throws SQLException;
}
