package com.climb.common.request.filter;

import com.climb.common.request.wrapper.ReusableRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lht
 * @since 2021/3/1 14:16
 */
@WebFilter
@Order(Integer.MIN_VALUE)
public class RequestWrapperFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
        String contentType = servletRequest.getContentType();
        //特殊类型请求，不做请求包装
        if(!StringUtils.isEmpty(contentType)&&contentType.contains("multipart/form-data")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            //Read request.getBody() as many time you need
            filterChain.doFilter(new ReusableRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
        }
    }
}