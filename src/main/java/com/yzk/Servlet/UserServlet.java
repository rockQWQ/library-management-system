package com.yzk.Servlet;


import com.yzk.Bean.User;
import com.yzk.Service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserServlet")
public class UserServlet extends ThymeleafViewServlet{
    UserServiceImpl userService=new UserServiceImpl();
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        super.processTemplate("login", request, response);
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User login = userService.login(username, password);
            if (login != null) {
                HttpSession session = req.getSession();
                session.setAttribute("User", login);
                req.getRequestDispatcher("/backgroundsystem/BorrowingServlet?method=query").forward(req, resp);
            } else {
                req.setAttribute("info", "用户名或密码错误");
                super.processTemplate("login", req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
