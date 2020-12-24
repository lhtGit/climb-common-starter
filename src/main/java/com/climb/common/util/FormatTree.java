package com.climb.common.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 树工具
 * @author lht
 * @since 2020/10/14 10:03
 */
public class FormatTree {
    /**
     * @author lht
     * @doc   将同一类的组装到同一数组中
     * @date 2018/9/11
     * @param l 多种类数据结构
    * @param codes 判定同一种类属性
     * @return
     */
    public static <T> List<List<T>>  getListTree(List<T> l,String ... codes){
        List<List<T>> mapList = new ArrayList<>();
        T proctemProcess = null;
        List<T> list = null;
        for(int i=0; i< l.size();i++){
            T relProctemProcess = l.get(i);
            //中间部分
            if(i>0){
                if(FormatTree.isEqual(proctemProcess,relProctemProcess,codes)){
                    list.add(proctemProcess);
                }else{
                    list.add(proctemProcess);
                    mapList.add(list);
                    list = new ArrayList<>();
                }
                //第一个
            }else if(i==0){
                list = new ArrayList<>();
            }
            //最后一个
            if(i==l.size()-1){
                list.add(relProctemProcess);
                mapList.add(list);
            }
            proctemProcess = relProctemProcess;
        }
        return mapList;
    }

    /**
     * @author lht
     * @doc   通过封装类的属性值判断list是否相等(一级)
     * @date 2018/9/11
     * @param list1
    * @param list2
    * @param codes  判断属性值
     * @return
     */
    public static <T> boolean isEqual(List<T> list1,List<T> list2,String... codes){
        //1. 模糊判断size是否一样
        if(list1==null||list2==null||list1.size()!=list2.size()){
            return false;
        }
        //2. 具体判断对应的属性值是否一致
        for(T t1:list1){
            for(T t2:list2){
                for(String code:codes){
                    if(!getMethod(t1,code).equals(getMethod(t2,code))){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @author lht
     * @doc   通过封装类的属性值判断list是否相等(一级)
     * @date 2018/9/11
     * @param t1
    * @param t2
    * @param codes
     * @return
     */
    public static <T> boolean isEqual(T t1,T t2,String ...codes){
        if(t1==null||t2==null){
            return false;
        }
        for(String code:codes){
            if(!getMethod(t1,code).equals(getMethod(t2,code))){
                return false;
            }
        }
        return true;
    }

    /**
     * @Author: lht
     * @Description: 获得树形
     * @Date: 2018/12/18 9:39
     * @param data 有父id的数据集(pid为null或者“” 为根节点)
     * @param idCode id code
     * @param pidCode pid code
     * @param chilrensCode  子集code
     * @return: java.util.List<T>
     */
    public static <T> List<T> getTree(List<T> data,String idCode,String pidCode,String chilrensCode){
        //获得根目录
        List<T> result= new ArrayList<>();
        Map<Object,List<T>> map = new HashMap<>();
        //获得父级
        for(T t : data){
            if(StringUtils.isEmpty(getMethod(t,pidCode))){
                result.add(t);
            }
            List<T> tempList = map.get(getMethod(t,pidCode));
            if(tempList == null){
                tempList = new ArrayList<>();
            }
            tempList.add(t);
            map.put(getMethod(t,pidCode),tempList);
        }

        Stack<T> stack = new Stack<>();
        for(T t:result){
            stack.push(t);
            while(!stack.empty()){
                T temp = stack.pop();
                List<T> resultChildren = new ArrayList<>();
                List<T> tempList = map.get(getMethod(temp,idCode));
                if(tempList!=null&&tempList.size()>0){
                    for(T t2 : tempList){
                        stack.push(t2);
                        resultChildren.add(t2);
                    }
                    setMethod(temp,chilrensCode,resultChildren);
                }
            }

        }
        return result;
    }


    /**
     * @author lht
     * @doc   获得属性的相应get方法值
     * @date 2018/9/11
     * @param obj 设置类实例
    * @param code 设置类属性
     * @return
     */
    private static Object getMethod(Object obj,String code)  {
        Object o = null;
        try{
            Class clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(code,clazz);
            //获得get方法
            Method getMethod = pd.getReadMethod();
            //执行get方法返回一个Object
            o  = getMethod.invoke(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StringUtils.isEmpty(o)?"":o;
    }

    /**
     * @Author: lht
     * @Description: 设置属性的相应set方法
     * @Date: 2018/12/18 9:36
     * @param obj 设置类实例
     * @param code 设置类属性
     * @param v 设置值
     * @return: void
     */
    private static void setMethod(Object obj,String code,Object... v)  {
        try{
            Class clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(code,clazz);
            //获得get方法
            Method setMethod = pd.getWriteMethod();
            //执行set方法返回一个Object
            setMethod.invoke(obj,v);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
