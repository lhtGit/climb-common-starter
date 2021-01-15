package com.climb.common.user.util;

import com.alibaba.fastjson.JSON;
import com.climb.common.constant.CommonConstant;
import com.climb.common.user.UserBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

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
     * 获取request中用户信息
     * @author lht
     * @since  2020/11/26 14:44
     */
    public static UserBaseInfo getUserDetails(HttpServletRequest request){
        UserBaseInfo userInfo = null;
        String userStr = request.getHeader(CommonConstant.USER_INFO);
        if(!StringUtils.isEmpty(userStr)){
            try{
                userInfo = JSON.parseObject(URLDecoder.decode(userStr,CommonConstant.UTF8), UserBaseInfo.class);
            }catch (Exception e){
                log.error("获取用户信息失败",e);
            }
        }
        //设置未登录用户信息
        if(userInfo==null){
            userInfo = new UserBaseInfo();
            userInfo.setId(NONE_USER_ID);
            userInfo.setName("未登录");
        }
        return userInfo;
    }

    /**
     * 判断是为未登录，没有用户信息
     * @author lht
     * @since  2020/11/26 14:45
     */
    public static boolean isNoneUser(UserBaseInfo userInfo){
        return userInfo==null||NONE_USER_ID.equals(userInfo.getId());
    }
}
