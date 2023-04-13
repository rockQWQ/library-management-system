package com.yzk.Dao;

import com.yzk.Bean.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有分类信息
     * @return
     * @throws SQLException
     */
    List<Category> queryList() throws SQLException;

    int insert(Category category)throws SQLException;

    int delete(int id) throws SQLException;

    int update(Category id)throws SQLException;
}
