package com.climb.common.handler;

import cn.hutool.core.date.DateUtil;
import com.climb.common.bean.Log;
import com.climb.common.request.wrapper.ReusableRequestWrapper;
import com.climb.common.user.bean.UserInfoBase;
import com.climb.common.user.util.UserUtils;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author lht
 * @since 2021/3/1 09:16
 */
@Aspect
@Component
@Slf4j
public class LogInterceptor {

    /**
     * http method get params by body
     */
    private Set<String> METHOD_TO_BODY_SET = Sets.newHashSet(HttpMethod.POST.name(),HttpMethod.PUT.name(),HttpMethod.PATCH.name());

    @Around("execution(* com.climb.*.controller..*(..))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable{
        Log logInfo = new Log();
        //request
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        UserInfoBase userInfoBase = UserUtils.getUserDetails(request);
        logInfo.setMethod(request.getMethod());
        logInfo.setUrl(request.getRequestURI());
        logInfo.setUserId(userInfoBase.getId());
        logInfo.setUserName(userInfoBase.getName());
        logInfo.setStartTime(DateUtil.date().toMsStr());
        logInfo.setIsSuccess(Boolean.TRUE);
        try{
            if(request instanceof ReusableRequestWrapper){
                logInfo.setRequestParams(getParams((ReusableRequestWrapper)request));;
            }else{
                log.warn("HttpServletRequest 的实现类不是 ReusableRequestWrapper。");
            }
            return pjp.proceed();
        }catch (Exception e){
            logInfo.setException(e);
            logInfo.setIsSuccess(Boolean.FALSE);
            throw e;
        }finally {
            logInfo.setEndTime(DateUtil.date().toMsStr());
            if(logInfo.getIsSuccess()){
                log.info(logInfo.toString());
            }else{
                log.error(logInfo.toString(),logInfo.getException());
            }
        }
    }

    /**
     * 获取请求参数
     * @author lht
     * @since  2021/3/1 14:46
     * @param  request
     */
    private String  getParams(ReusableRequestWrapper request){
        String method = request.getMethod();
        if(METHOD_TO_BODY_SET.contains(method)){
            return request.getBody().replaceAll("[\r\n]", "");
        }else{
            StringBuilder logParams = new StringBuilder();
            Map<String,String[]> parameterMap = request.getParameterMap();
            parameterMap.keySet().forEach(key -> logParams.append(key)
                    .append(":")
                    .append(request.getParameter(key))
                    .append(",")
            );
            return "{"+(logParams.length()>0?logParams.deleteCharAt(logParams.length()-1).toString():"")+"}";
        }
    }

}
