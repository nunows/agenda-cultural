package auth;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;


public class Filtro implements Filter {

    private static final String URL = "http://localhost:8080/webapp/login.html";
    private FilterConfig filterConfig = null;

    public void init(FilterConfig arg0) throws ServletException {
        this.filterConfig = arg0;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
        boolean estado = false;

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession sessao = request.getSession();

        try {
            estado = (Boolean) sessao.getAttribute("login");
        } catch (Exception e) {
            estado = false;
        }
        
        if (!estado){
            response.sendRedirect(URL);
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
