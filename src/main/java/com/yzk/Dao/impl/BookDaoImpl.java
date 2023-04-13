package com.yzk.Dao.impl;

import com.yzk.Bean.Book;
import com.yzk.Bean.Category;
import com.yzk.Dao.BookDao;
import com.yzk.jdbcUtil.JdbcUtil;
import com.yzk.jdbcUtil.PageInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDaoImpl implements BookDao {
    @Override
    public PageInfo queryBook(Map<String, String> params, int pageNum, int pageSize) throws SQLException, IllegalAccessException {
        String sql = "select * from book";
        return JdbcUtil.query(sql, null, Book.class, pageNum, pageSize);
    }

    @Override
    public int delete(int id) throws SQLException {
        String sql = "delete from book where id=?";
        return JdbcUtil.update(sql, new Object[]{id});
    }

    @Override
    public int insert(Book book) throws SQLException {
        String sql = "insert into book(name,description,author,publis,category,categoryid,Inventory) values(?,?,?,?,?,?,?)";
        return JdbcUtil.update(sql, new Object[]{book.getName(), book.getDescription(), book.getAuthor(), book.getPublis(), book.getCategory(), book.getCategoryid(), book.getInventory()});
    }

    @Override
    public List<Book> query(Object[] objects) throws SQLException {
        String sql = "select * from book where 1=1";
        return JdbcUtil.query(sql, objects, Book.class);

    }

    @Override
    public List<Book> updateBookReqeust(int id) throws SQLException {
        String sql = "select * from book where id=?";
        return JdbcUtil.query(sql, new Object[]{id}, Book.class);
    }

    @Override
    public int updateBook(Book book) throws SQLException {
        String sql = "update book set name=?,description=?,author=?,publis=?,category=?,categoryid=?,Inventory=? where id=?";
        return JdbcUtil.update(sql,new Object[]{book.getName(),book.getDescription(),book.getAuthor(),book.getPublis(),book.getCategory(),book.getCategoryid(),book.getInventory(),book.getId()});
    }
}
