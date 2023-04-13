package com.yzk.filter;


import com.yzk.Bean.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/backgroundsystem/*", "/view/backgroundsystem/*"})
public class IsLoginFilter implements Filter {
    private TemplateEngine templateEngine;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;//父类向子类转型
        Object object = req.getSession().getAttribute("User");//通过子类getSession会话查找是否有managerBean对应的值
        String subReg = req.getParameter("submit");
        String butLogin = req.getParameter("method");
        if (object != null && object instanceof User || (subReg != null && subReg.equals("用户注册") || butLogin.equals("login"))) {
            filterChain.doFilter(servletRequest, servletResponse);//放行(继续执行后面url)
        } else {
            this.initThymeleaf(servletRequest.getServletContext());//初始化引擎
            req.setAttribute("info", "请先登录后访问");
            this.processTemplate("login", req, (HttpServletResponse) servletResponse, req.getServletContext());//跳转
        }
    }

    public void initThymeleaf(ServletContext servletContext) {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/view/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        WebContext webContext = new WebContext(request, response, servletContext);
        this.templateEngine.process(templateName, webContext, response.getWriter());
    }
}
