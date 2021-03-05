package com.climb.common.request.filter;

import com.climb.common.request.wrapper.ReusableRequestWrapper;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 包装request请求
 * 在包装请求时需要注意不能再HiddenHttpMethodFilter之前包装，应该HiddenHttpMethodFilter过滤器是转化参数请求的，避免因为包装请求后不能正确转化参数
 *
 * HiddenHttpMethodFilter：
 *  浏览器form表单只支持GET与POST请求，而DELETE、PUT等method并不支持，spring3.0添加了一个过滤器，
 *  可以将这些请求转换为标准的http方法，使得支持GET、POST、PUT与DELETE请求，该过滤器为HiddenHttpMethodFilter
 * @author lht
 * @since 2021/3/1 14:16
 */
@WebFilter
@Order
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
