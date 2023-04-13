package com.yzk.Service.impl;

import com.yzk.Bean.Category;
import com.yzk.Dao.impl.CategoryDaoImpl;
import com.yzk.Service.CategoryServlet;

import java.sql.SQLException;
import java.util.List;

public class CategoryServletImpl implements CategoryServlet {
    CategoryDaoImpl categoryDao= new CategoryDaoImpl();
    @Override
    public List<Category> queryList() throws SQLException {
        return categoryDao.queryList();
    }

    @Override
    public int insert(Category category)throws SQLException{
        return categoryDao.insert(category);

    }

    @Override
    public int delete(int id) throws SQLException {
        return categoryDao.delete(id);
    }

    @Override
    public int update(Category category)throws SQLException{
        return categoryDao.update(category);
    }

}
