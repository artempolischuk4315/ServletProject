package ua.polischuk.filter;

import ua.polischuk.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig)  {}

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) res;


        if ((request.getRequestURI().contains("user") || request.getRequestURI().contains("admin"))
                && request.getSession().getAttribute("role") == null) {

            response.sendRedirect(request.getContextPath() + "/");
        }

        if (request.getRequestURI().contains("admin") && ((request.getSession().getAttribute("role").equals(User.ROLE.USER)))) {

            response.sendRedirect(request.getContextPath() + "/user/user-hello.jsp");
        }

        if (( request.getRequestURI().contains("index")||isOnIndexPage(request)
                || request.getRequestURI().contains("login.jsp") || request.getRequestURI().contains("registration")) &&
                    request.getSession().getAttribute("role") != null) {



            request.getSession().setAttribute("role", null);

            response.sendRedirect(request.getContextPath()+"/logout");
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