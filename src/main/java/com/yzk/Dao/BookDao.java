package com.yzk.Dao;



import com.yzk.Bean.Book;
import com.yzk.jdbcUtil.PageInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BookDao {
    /*
     * 执行分页查询，将信息保存到pageInfoBean中返回
     */
    PageInfo queryBook(Map<String, String> params, int pageNum, int pageSize) throws SQLException, IllegalAccessException;

    int delete(int id) throws SQLException;

    int insert(Book book) throws SQLException;

    List<Book> query(Object[] objects)throws SQLException;

    List<Book> updateBookReqeust(int id) throws SQLException;

    int updateBook(Book book) throws SQLException;
}
