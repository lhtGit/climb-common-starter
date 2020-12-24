package com.climb.common.bean;

import com.google.common.collect.Maps;
import lombok.*;

import java.util.Map;

/*
 *
 * @Author lht
 * @Date  2020/9/14 09:30
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> extends Result<T> {

    private long total;

    private long page;

    private long size;

    @Override
    public Map<String,Object> toMap(){
        Map<String,Object> map = Maps.newHashMap();
        map.put("code",this.getCode());
        map.put("msg",this.getMsg());
        map.put("data",this.getData());
        map.put("dataList",this.getDataList());
        map.put("total",this.getTotal());
        map.put("page",this.getPage());
        map.put("size",this.getSize());
        return map;
    }
}
