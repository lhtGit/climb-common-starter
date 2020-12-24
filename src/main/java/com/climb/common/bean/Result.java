package com.climb.common.bean;

import com.climb.common.constant.CommonConstant;
import com.google.common.collect.Maps;
import lombok.*;

import java.util.Collection;
import java.util.Map;

/*
 *
 * @Author lht
 * @Date  2020/9/14 09:28
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public  class Result<T> {

    private int code;

    private String msg;

    private T data;

    private Collection<T> dataList;

    public boolean isSuccess(){
        return code== CommonConstant.SUCCESS_CODE;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("code",this.getCode());
        map.put("msg",this.getMsg());
        map.put("data",this.getData());
        map.put("dataList",this.getDataList());
        return map;
    }
}
