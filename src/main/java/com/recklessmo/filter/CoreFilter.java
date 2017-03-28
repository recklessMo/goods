package com.recklessmo.filter;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by hpf on 3/28/17.
 */
public class CoreFilter implements Filter{

    public static Log logger = LogFactory.getLog(CoreFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long gap = System.currentTimeMillis() - start;
        StringBuilder sb = new StringBuilder();
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        sb.append("client: ");
        sb.append(httpServletRequest.getRemoteAddr());
        sb.append(" url: ");
        sb.append(httpServletRequest.getRequestURI());
        sb.append(" ");
        sb.append("totaltime: ");
        sb.append(gap);
        logger.info(sb.toString());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
