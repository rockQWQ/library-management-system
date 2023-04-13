package com.yzk.Dao.impl;

import com.yzk.Bean.Category;
import com.yzk.Dao.CategoryDao;
import com.yzk.jdbcUtil.JdbcUtil;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> queryList() throws SQLException {
        String sql = "select * from category";

        List<Category> query = JdbcUtil.query(sql, null,Category.class);
        return query;
    }

    @Override
    public int insert(Category category) throws SQLException{
        String sql = "insert into category(name) values(?)";
        return JdbcUtil.update(sql, new Object[]{category.getName()});
    }

    @Override
    public int delete(int id) throws SQLException {
        String sql = "delete from category where id=?";
        return JdbcUtil.update(sql, new Object[]{id});
    }

    @Override
    public int update(Category category)throws SQLException{
        String sql = "update category set name=? where id=?";
        return JdbcUtil.update(sql,new Object[]{category.getName(),category.getId()});
    }

}
