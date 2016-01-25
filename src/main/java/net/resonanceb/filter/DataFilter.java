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

/**
 * Servlet Filter implementation class DataFilter
 */
@WebFilter(filterName="/DataFilter", urlPatterns="/*")
public class DataFilter implements Filter {

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

        // get if we think this session data is new or not
        Boolean isNew = (request.getSession().getAttribute("new") != null ? (Boolean)request.getSession().getAttribute("new") : false);

        // if it is not new data clear out the old data, like whoa
        if (!isNew) {
            request.getSession().removeAttribute("data");
            request.getSession().removeAttribute("error");
        }

        // always clear the new attribute
        request.getSession().removeAttribute("new");

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