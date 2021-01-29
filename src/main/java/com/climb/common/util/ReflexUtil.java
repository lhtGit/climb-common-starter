package com.climb.common.util;

import com.climb.common.exception.GlobalException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射常用方法
 * @author lht
 * @since 2021/1/29 17:01
 */
public class ReflexUtil {
    private ReflexUtil() {
    }

    /**
     * 缓存get/set方法
     */
    private final static ConcurrentHashMap<Class<?>,ConcurrentHashMap<String, SoftReference<PropertyDescriptor>>> CACHE_MEHTOD_MAP
            = new ConcurrentHashMap<>();



    /**
     * 反射属性get方法
     * @author lht
     * @since  2021/1/29 17:06
     * @param obj
     * @param code
     */
    public static Object get(Object obj,String code){
        Class<?> clazz = obj.getClass();
        try {
            return getAndCache(clazz,code).getReadMethod().invoke(obj);
        } catch (Exception e) {
            throw new GlobalException("反射属性get方法获取值异常：",e);
        }
    }

    /**
     * 反射属性set方法
     * @author lht
     * @since  2021/1/29 17:06
     * @param obj
     * @param code
     * @param val
     */
    public static Object set(Object obj,String code,Object... val){
        Class<?> clazz = obj.getClass();
        try {
            return getAndCache(clazz,code).getWriteMethod().invoke(obj,val);
        } catch (Exception e) {
            throw new GlobalException("反射属性set方法获取值异常：",e);
        }
    }
    /**
     * 获取/创建 PropertyDescriptor 并缓存
     * @author lht
     * @since  2021/1/29 16:59
     * @param clazz
     * @param code
     */
    private static PropertyDescriptor getAndCache(Class<?> clazz, String code) throws IntrospectionException {
        ConcurrentHashMap<String, SoftReference<PropertyDescriptor>> mehtodMap =  CACHE_MEHTOD_MAP.get(clazz);

        if(mehtodMap==null){
            synchronized (CACHE_MEHTOD_MAP){
                mehtodMap = new ConcurrentHashMap<>();
                CACHE_MEHTOD_MAP.put(clazz,mehtodMap);
                SoftReference<PropertyDescriptor> softReference = createPropertyDescriptor(clazz,code);
                mehtodMap.putIfAbsent(code, softReference);
                return softReference.get();
            }
        }
        SoftReference<PropertyDescriptor> softReference = mehtodMap.get(code);
        if(softReference == null){
            SoftReference<PropertyDescriptor> descriptorSoftReference = createPropertyDescriptor(clazz,code);
            mehtodMap.putIfAbsent(code, descriptorSoftReference);
            return descriptorSoftReference.get();
        }
        return softReference.get();
    }

    /**
     * 创建 PropertyDescriptor
     * @author lht
     * @since  2021/1/29 17:00
     * @param clazz
     * @param code
     */
    private static SoftReference<PropertyDescriptor> createPropertyDescriptor(Class<?> clazz,String code) throws IntrospectionException {
        return  new SoftReference<>(new PropertyDescriptor(code,clazz));
    }
}
