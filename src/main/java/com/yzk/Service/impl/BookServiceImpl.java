package com.yzk.Service.impl;

import com.yzk.Bean.Book;
import com.yzk.Bean.Category;
import com.yzk.Dao.impl.BookDaoImpl;
import com.yzk.Dao.impl.CategoryDaoImpl;
import com.yzk.Service.BookService;
import com.yzk.jdbcUtil.PageInfo;
import com.yzk.vo.BookVo;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements BookService {
    BookDaoImpl bookdao = new BookDaoImpl();
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    public PageInfo queryBook(Map<String, String> params, int pageNum, int pageSize) throws SQLException, IllegalAccessException {
        PageInfo pageInfo = bookdao.queryBook(params, pageNum, pageSize);
        System.out.println(pageInfo);
        return pageInfo;
    }

    @Override
    public int delete(int id) throws SQLException {
        return bookdao.delete(id);
    }

    @Override
    public int insert(Book book) throws SQLException {
        return bookdao.insert(book);
    }

    public List<BookVo> copyList(List<Book> books) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<BookVo> bookVos = new ArrayList<>();
        for (Book bookVo : books) {
            bookVos.add(copy(bookVo));
        }
        System.out.println(bookVos);
        return bookVos;
    }

    public BookVo copy(Book book) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<Category> categories = categoryDao.queryList();
        System.out.println(categories);
        BookVo bookVo = new BookVo();
        BeanUtils.copyProperties(bookVo, book);
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == book.getCategoryid()) {
                bookVo.setCategoryname(categories.get(i).getName());
            }
        }

        return bookVo;
    }

    @Override
    public Book updateBookReqeust(int id) throws SQLException {
        List<Book> bList = bookdao.updateBookReqeust(id);
        System.out.println(bList);
        if (bList.size() != 0) {
            return bList.get(0);
        }
        return null;
    }

    @Override
    public int updateBook(Book book) throws SQLException {
        return bookdao.updateBook(book);
    }

    @Override
    public List<Book> queryBook(Object[] objects) throws SQLException {
        return bookdao.query(objects);
    }
}
