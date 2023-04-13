package com.yzk.Servlet;


import com.yzk.Bean.Category;
import com.yzk.Service.impl.CategoryServletImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
@WebServlet("/backgroundsystem/CategoryServlet")
public class CategoryServlet extends ThymeleafViewServlet {
    CategoryServletImpl categoryServlet = new CategoryServletImpl();

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            List<Category> categories = categoryServlet.queryList();
            req.setAttribute("categories", categories);
            super.processTemplate("backgroundsystem/query_all_classifications", req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
            String name = req.getParameter("name");
            Category category = new Category();
            category.setName(name);
            int cate = categoryServlet.insert(category);
            req.setAttribute("cate", cate==1?"添加成功":"添加失败");
            req.getRequestDispatcher("/backgroundsystem/CategoryServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void insertRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("backgroundsystem/Add_Categor", req, resp);
    }
    public void  updateReqeust(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("id",id);
        super.processTemplate("backgroundsystem/Update_Categor", req, resp);
    }
    public void  update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        Category category = new Category();
        category.setId(Integer.parseInt(id));
        category.setName(name);
        try {
            int cate = categoryServlet.update(category);
            req.setAttribute("cate", cate==1?"修改成功":"修改失败");
            req.getRequestDispatcher("/backgroundsystem/CategoryServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void  delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            int cate = categoryServlet.delete(Integer.parseInt(id));
            req.setAttribute("cate", cate==1?"删除成功":"删除失败");
            req.getRequestDispatcher("/backgroundsystem/CategoryServlet?method=query").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
