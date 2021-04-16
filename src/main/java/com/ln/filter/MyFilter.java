package com.ln.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author： 张世杰
 * @date： 2021-04-14 16:22
 */
public class MyFilter implements Filter {
    Set<String> notCheckUrl = new HashSet<String>();
    //过滤器要是需要在web.xml中配置参数，读取的话，在init中读取
    public void init(FilterConfig filterConfig) throws ServletException {
        String Urls = filterConfig.getInitParameter("notCheckUrl");
        for (String url : Urls.split(",")) {
            notCheckUrl.add(url.trim());
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /**
         * 要是这个请求过来的url是不需要过滤的，不管怎么样都直接放行
         */
       HttpServletRequest req= (HttpServletRequest)request;
        String uri = req.getRequestURI();
        if (notCheckUrl.contains(uri)){
            //直接放行
            chain.doFilter(request, response);
        }else {
            //需要过滤的，首先要判断他有没有登录，要是没有登录回去登录
            Object ub = req.getSession().getAttribute("ub");
            if (ub==null){
                //他不是特殊的，也没有登录，直接让去登录就欧克啦
                req.getRequestDispatcher("/index.html").forward(request, response);
            }else {
                chain.doFilter(request, response);
            }
        }
    }

    public void destroy() {

    }
}
