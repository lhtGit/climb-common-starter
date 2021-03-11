package com.climb.common.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获得ApplicationContext
 * @author lht
 * @since 2020/12/22 10:18
 */
@Component("applicationContextUtil")
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ConfigurableApplicationContext context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            ApplicationContextUtil.context =  (ConfigurableApplicationContext) applicationContext;
        }

    }

    /**
     * 根据接口class获取所有注入到spring的实现类
     * @author lht
     * @since  2020/12/25 12:16
     * @param clazz
     */
    public static <T> List<T> getBeanByInterface(Class<T> clazz){
        String[] strings= ApplicationContextUtil.context.getBeanNamesForType(clazz);
        return Lists.newArrayList(strings)
                .stream()
                .map(beanName -> ApplicationContextUtil.context.getBean(beanName,clazz))
                .collect(Collectors.toList());
    }
    /**
     * 获取bean
     * @author lht
     * @since  2020/12/25 12:12
     * @param clazz
     */
    public static <T> T getBean(Class<T> clazz){
        return ApplicationContextUtil.context.getBean(clazz);
    }

    /**
     * 停止项目
     * @author lht
     * @since  2020/12/22 10:19
     */
    public static void showdown(){
        if (null != ApplicationContextUtil.context){
            ApplicationContextUtil.context.close();
        }
    }
}
