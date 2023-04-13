package com.yzk.Servlet;


import com.yzk.Bean.Book;
import com.yzk.Bean.Category;
import com.yzk.Service.impl.BookServiceImpl;
import com.yzk.Service.impl.CategoryServletImpl;
import com.yzk.jdbcUtil.PageInfo;
import com.yzk.vo.BookVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/backgroundsystem/BookServlet")
public class BookServlet extends ThymeleafViewServlet {
    BookServiceImpl serviceimpl = new BookServiceImpl();
    CategoryServletImpl categoryServlet = new CategoryServletImpl();

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InvocationTargetException {
        /* String[] condition = {"id", "name", "author", "publis", "category", "categoryid", "Inventory"};*/
        String pageNumStr = req.getParameter("pageNum");
        if (pageNumStr == null || pageNumStr.equals("")) {
            pageNumStr = "1";
        }

        try {
           /* Map<String, String> params = new HashMap<>();
            if (req.getParameter("id") == null) {
                for (int i = 0; i < condition.length; i++) {
                    String value = req.getParameter(condition[i]);
                    if (value != null && !value.equals("") && !value.equals("null")) {
                        if (condition[i]=="categoryid"){
                            params.put(condition[i], req.getParameter("categoryname"));
                        }
                        params.put(condition[i], req.getParameter(condition[i]));
                    }
                }
            }*/

            PageInfo pageInfo = serviceimpl.queryBook(null, Integer.parseInt(pageNumStr), 5);

            List list = serviceimpl.copyList(pageInfo.getDate());
            pageInfo.setDate(list);
            System.out.println(list);
            req.setAttribute("pageInfo", pageInfo);
            super.processTemplate("backgroundsystem/query_book", req, resp);


        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Book book = new Book();
            book.setName(req.getParameter("name"));
            book.setDescription(req.getParameter("description"));
            book.setAuthor(req.getParameter("author"));
            book.setPublis(req.getParameter("publis"));
            book.setCategory(req.getParameter("category"));
            ;
            book.setCategoryid(Integer.parseInt(req.getParameter("categoryid")));
            book.setInventory(Integer.parseInt(req.getParameter("Inventory")));

            int cate = serviceimpl.insert(book);
            req.setAttribute("cate", cate == 1 ? "添加成功" : "添加失败");
            req.getRequestDispatcher("/backgroundsystem/BookServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Category> categories = categoryServlet.queryList();
            req.setAttribute("categories", categories);
            super.processTemplate("backgroundsystem/Add_Book", req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateReqeust(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Category> categories = categoryServlet.queryList();
            Book book = serviceimpl.updateBookReqeust(Integer.valueOf(req.getParameter("id")));
            req.setAttribute("categories", categories);
            req.setAttribute("book", book);
            super.processTemplate("backgroundsystem/UpdateReqeust_Categor", req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Book book = new Book();
            book.setId(Integer.valueOf(req.getParameter("id")));
            book.setName(req.getParameter("name"));
            book.setAuthor(req.getParameter("author"));
            book.setDescription(req.getParameter("description"));
            book.setCategory(req.getParameter("category"));
            book.setPublis(req.getParameter("publis"));
            book.setCategoryid(Integer.valueOf(req.getParameter("categoryid")));
            book.setInventory(Integer.valueOf(req.getParameter("Inventory")));

            int i = serviceimpl.updateBook(book);
            req.setAttribute("cate", i == 1 ? "修改成功" : "修改失败");
            req.getRequestDispatcher("/backgroundsystem/BookServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            int cate = serviceimpl.delete(Integer.parseInt(id));
            req.setAttribute("cate", cate == 1 ? "删除成功" : "删除失败");
            req.getRequestDispatcher("/backgroundsystem/BookServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
