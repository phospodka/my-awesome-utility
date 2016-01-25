package net.resonanceb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.resonanceb.util.FileUtil;

/**
 * Servlet Filter implementation class PropertiesFilter
 */
@WebFilter(filterName="/PropertiesFilter", urlPatterns="/*")
public class PropertiesFilter implements Filter {

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // get the application properties and continually apply them to the session (to maybe handle live changes!!)
        request.getSession().setAttribute("props", FileUtil.loadProperties());

        // pass the request along the filter chain
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }
}