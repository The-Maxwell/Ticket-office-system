package servlets;

import javax.servlet.*;
import java.io.IOException;

public class CharEncodingFilter implements Filter {

    private FilterConfig filterConfig = null;
    private String encoding = null;

    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
        this.encoding = this.filterConfig.getInitParameter("encoding");
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 	throws IOException, ServletException {
        if (encoding != null) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }
}
