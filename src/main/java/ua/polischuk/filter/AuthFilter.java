package ua.polischuk.filter;


import ua.polischuk.controller.command.LogOut;
import ua.polischuk.model.entity.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig)  {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        boolean isAdminInRequest = request.getRequestURI().contains("admin");

        if ((request.getRequestURI().contains("user") || isAdminInRequest)
                && request.getSession().getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/");
        }
        if (request.getRequestURI().contains("admin") && ((request.getSession().getAttribute("role").equals(User.ROLE.USER)))) {

            response.sendRedirect(request.getContextPath() + "/user/user-hello.jsp");
        }
        if (( request.getRequestURI().contains("index")||isOnIndexPage(request) || request.getRequestURI().contains("login") || request.getRequestURI().contains("registration")) &&
                    request.getSession().getAttribute("role") != null) {
            request.getSession().setAttribute("role", null);
            new LogOut().execute(request);
        }

            chain.doFilter(request, response);

    }

    private boolean isOnIndexPage(HttpServletRequest request){
        String path = request.getContextPath()+"/";
        if(request.getRequestURI().equalsIgnoreCase(path)) return true;
        return false;
    }

    public void destroy() {}

}