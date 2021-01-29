package com.climb.common.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;

/**
 * 树工具
 * @author lht
 * @since 2020/10/14 10:03
 */
public class FormatTree {

    /**
     * 格式化为树形结构
     * @author lht
     * @since  2021/1/29 17:26
     * @param data 有父id的数据集
     * @param idCode id code
     * @param pidCode pid code
     * @param chilrensCode  子集code
     */
    public static <T> List<T> formatToTree(List<T> data,String idCode,String pidCode,String chilrensCode){
        return formatToTree(data,idCode,pidCode,chilrensCode,t -> StringUtils.isEmpty(ReflexUtil.get(t,pidCode)));
    }

    /**
     * 格式化为树形结构
     * @author lht
     * @since  2021/1/29 17:26
     * @param data 有父id的数据集
     * @param idCode id code
     * @param pidCode pid code
     * @param chilrensCode  子集code
     * @param isParent 判断是否为父级
     */
    public static <T> List<T> formatToTree(List<T> data, String idCode, String pidCode, String chilrensCode, Function<T,Boolean> isParent){
        //获得根目录
        List<T> result= new ArrayList<>();
        Map<Object,List<T>> map = new HashMap<>();
        //获得父级
        for(T t : data){
            if(isParent.apply(t)){
                result.add(t);
            }
            List<T> tempList = map.get(ReflexUtil.get(t,pidCode));
            if(tempList == null){
                tempList = new ArrayList<>();
            }
            tempList.add(t);
            map.put(ReflexUtil.get(t,pidCode),tempList);
        }

        Stack<T> stack = new Stack<>();
        for(T t:result){
            stack.push(t);
            while(!stack.empty()){
                T temp = stack.pop();
                List<T> resultChildren = new ArrayList<>();
                List<T> tempList = map.get(ReflexUtil.get(temp,idCode));
                if(tempList!=null&&tempList.size()>0){
                    for(T t2 : tempList){
                        stack.push(t2);
                        resultChildren.add(t2);
                    }
                    ReflexUtil.set(temp,chilrensCode,resultChildren.toArray());
                }
            }

        }
        return result;
    }


    /**
     * 转换树形集合为普通集合，扁平化处理;
     * 同时会清除子节点引用
     * @param treeData 树形集合
     * @param childrenCode 子节点属性
     * @author liuhuibin
     * @since 2021/1/14 4:38 下午
     */
    public static <T> List<T> formatToList(List<T> treeData, String childrenCode) {
        return formatToList(treeData, childrenCode, true);
    }
    /**
     * 转换树形集合为普通集合，扁平化处理。
     * @param treeData 树形集合
     * @param childrenCode 子节点属性
     * @param clearChildren 是否清除子节点引用,转换后不再使用子节点时建议清除
     * @return
     * @author liuhuibin
     * @since 2021/1/13 5:50 下午
     */
    @SuppressWarnings("all")
    public static <T> List<T> formatToList(List<T> treeData, String childrenCode,boolean clearChildren) {
        List<T> result = new ArrayList<>();
        if(treeData==null){
            return result;
        }
        Stack<Collection<T>> stack = new Stack<>();
        stack.push(treeData);
        while (!stack.empty()) {
            Collection<T> list = stack.pop();
            if (!list.isEmpty()) {
                list.forEach(item -> {
                    result.add(item);
                    Collection<T> children = (Collection<T>) ReflexUtil.get(item, childrenCode);
                    if(children!=null&&!children.isEmpty()){
                        stack.push(children);
                        // 释放子节点的对象引用
                        if(clearChildren){
                            ReflexUtil.set(item, childrenCode, (Object) null);
                        }
                    }
                });
            }
        }
        return result;
    }
}
