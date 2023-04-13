package com.yzk.Service;

import com.yzk.Bean.Book;
import com.yzk.Dao.impl.BookDaoImpl;
import com.yzk.jdbcUtil.PageInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BookService {

    PageInfo queryBook(Map<String, String> params, int pageNum, int pageSize) throws SQLException, IllegalAccessException;

    int delete(int id) throws SQLException;

    int insert(Book book) throws SQLException;

    Book updateBookReqeust(int id) throws SQLException;

    int updateBook(Book book) throws SQLException;

    List<Book> queryBook(Object[] objects) throws SQLException;

}
