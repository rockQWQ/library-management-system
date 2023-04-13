package test;

import com.yzk.Bean.Book;
import com.yzk.Dao.impl.BookDaoImpl;
import com.yzk.Service.impl.BookServiceImpl;
import com.yzk.jdbcUtil.PageInfo;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class aa {

    @Test
    public void aa() throws SQLException, IllegalAccessException, InvocationTargetException {
        BookDaoImpl bookdao=new BookDaoImpl();
        List<Book> query = bookdao.query(null);
        System.out.println(query);

    }

}
