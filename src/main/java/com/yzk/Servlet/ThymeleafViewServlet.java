package com.yzk.Servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class ThymeleafViewServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() {
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/view/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String method = req.getParameter("method");
        if (method==null||method.equals("")){
            req.setAttribute("errorInfo", "method未携带参数,无法查找方法");
            this.processTemplate("error", req, resp);
            return;
        }

        Class<? extends ThymeleafViewServlet> oClass = this.getClass();
        try {
            Method om = oClass.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            om.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            req.setAttribute("errorInfo", "找不到:"+method+"方法");
            this.processTemplate("error", req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorInfo", method+"方法运行时产生问题:"+e.getMessage());
            this.processTemplate("error", req, resp);
        }
    }

    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        WebContext webContext = new WebContext(request, response, getServletContext());
        this.templateEngine.process(templateName, webContext, response.getWriter());
    }
}
