package com.yzk.Service.impl;

import com.yzk.Bean.Book;
import com.yzk.Bean.Borrowing;
import com.yzk.Bean.Category;
import com.yzk.Dao.impl.BookDaoImpl;
import com.yzk.Dao.impl.BorrowingDaoImpl;
import com.yzk.Service.BorrowingService;
import com.yzk.vo.BookVo;
import com.yzk.vo.BorrowingVo;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowingServiceImpl implements BorrowingService {
    BorrowingDaoImpl borrowingDao=new BorrowingDaoImpl();
    BookDaoImpl bookdao=new BookDaoImpl();
    @Override
    public List<Borrowing> queryList() throws SQLException {
        return borrowingDao.queryList();
    }

    public List<BorrowingVo> copyList(List<Borrowing> books) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<BorrowingVo> bookVos = new ArrayList<>();
        for (Borrowing bookVo : books) {
            bookVos.add(copy(bookVo));
        }
        System.out.println(bookVos);
        return bookVos;
    }

    public BorrowingVo copy(Borrowing book) throws InvocationTargetException, IllegalAccessException, SQLException {
        List<Book> query = bookdao.query(null);

        BorrowingVo borrowingVo = new BorrowingVo();
        BeanUtils.copyProperties(borrowingVo,book);
        for (int i = 0; i < query.size(); i++) {
            if (query.get(i).getId()==book.getBookid()){
                borrowingVo.setBookname(query.get(i).getName());
            }
        }
        return borrowingVo;
    }

    @Override
    public int insert(Borrowing borrowing) throws SQLException {
        return borrowingDao.insert(borrowing);
    }
}
