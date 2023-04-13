package com.yzk.Servlet;


import com.yzk.Bean.Book;
import com.yzk.Bean.Borrowing;
import com.yzk.Service.BookService;
import com.yzk.Service.impl.BookServiceImpl;
import com.yzk.Service.impl.BorrowingServiceImpl;
import com.yzk.vo.BorrowingVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/backgroundsystem/BorrowingServlet")
public class BorrowingServlet extends ThymeleafViewServlet {
    BorrowingServiceImpl borrowingService = new BorrowingServiceImpl();
    BookService bookService = new BookServiceImpl();

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {

        try {
            List<Borrowing> borrowings = borrowingService.queryList();
            List<BorrowingVo> borrowingVos = borrowingService.copyList(borrowings);
            req.setAttribute("borrowingVos", borrowingVos);
            super.processTemplate("backgroundsystem/index", req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Object[] objects = new Object[]{};
            List<Book> bList = bookService.queryBook(objects);
            req.setAttribute("bList", bList);
            super.processTemplate("backgroundsystem/lyear_pages_gallery", req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setBookid(Integer.valueOf(req.getParameter("bookid")));
            borrowing.setStartTime(req.getParameter("startTime"));
            borrowing.setEndTime(req.getParameter("endTime"));
            borrowing.setUsername(req.getParameter("username"));
            borrowing.setStatus(1);

            int i = borrowingService.insert(borrowing);
            System.out.println(i == 1 ? "添加成功" : "添加失败");
            req.getRequestDispatcher("/backgroundsystem/BorrowingServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
