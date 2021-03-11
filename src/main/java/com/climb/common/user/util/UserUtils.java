package com.climb.common.user.util;

import com.alibaba.fastjson.JSON;
import com.climb.common.constant.CommonConstant;
import com.climb.common.user.bean.UserInfoBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.function.Function;

/**
 * 获得用户信息的util
 * @author lht
 * @since 2020/9/28 10:44
 */
@Slf4j
public class UserUtils {
    //未登录的用户id为0
    public static final String NONE_USER_ID = "0";

    private UserUtils() {
    }


    /**
     * 获取request中用户信息，优先级是 任命用户信息-> gateway登录用户信息
     * @author lht
     * @since  2020/11/26 14:44
     */
    public static UserInfoBase getUserDetails(HttpServletRequest request){
        return doUserDetails(request,request1 -> {
            //尝试获取本地
            String userStr = (String)request.getAttribute(CommonConstant.APPOINT_USER_INFO);
            //尝试获取feign调用后设置在headers中
            if(userStr==null){
                userStr = request.getHeader(CommonConstant.APPOINT_USER_INFO);
            }
            //尝试获取gateway登录用户
            if(userStr==null){
                userStr = request.getHeader(CommonConstant.USER_INFO);
            }
            return userStr;
        });
    }

    /**
     * 获取gateway登录用户信息
     * @author lht
     * @since  2021/2/1 16:45
     * @param request
     */
    public static UserInfoBase getOriginalUserDetails(HttpServletRequest request){
        return doUserDetails(request,request1 -> request.getHeader(CommonConstant.USER_INFO));
    }

    /**
     * 在request中获取用户信息
     * @author lht
     * @since  2021/2/1 16:46
     * @param request
     * @param userStrFunction
     */
    private static UserInfoBase doUserDetails(HttpServletRequest request, Function<HttpServletRequest,String> userStrFunction){
        UserInfoBase userInfo;
        if(request==null){
            userInfo = new UserInfoBase();
            userInfo.setId(NONE_USER_ID);
            userInfo.setName("消息消费");
            return userInfo;
        }
        String userStr =  userStrFunction.apply(request);
        if(userStr==null){
            userStr = request.getHeader(CommonConstant.USER_INFO);
        }
        userInfo = parseUser(userStr);
        //设置未登录用户信息
        if(userInfo==null){
            userInfo = new UserInfoBase();
            userInfo.setId(NONE_USER_ID);
            userInfo.setName("未登录");
        }
        return userInfo;
    }


    /**
     * 解析用户信息
     * @author lht
     * @since  2021/3/11 13:44
     * @param  userStr
     */
    public static UserInfoBase parseUser(String userStr){
        if(StringUtils.isEmpty(userStr)){
            return null;
        }
        try {
            return JSON.parseObject(URLDecoder.decode(userStr,CommonConstant.UTF8), UserInfoBase.class);
        }catch (Exception e){
            log.error("解析用户信息失败",e);
        }
        return null;
    }

    /**
     * 判断是为未登录，没有用户信息
     * @author lht
     * @since  2020/11/26 14:45
     */
    public static boolean isNoneUser(UserInfoBase userInfo){
        return userInfo==null||NONE_USER_ID.equals(userInfo.getId());
    }
}
