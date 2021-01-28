package com.climb.common.bean;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("API分页返回结果")
public class PageResult<T> extends Result<T> {

    @ApiModelProperty(value = "总数")
    private long total;

    @ApiModelProperty(value = "页码")
    private long page;

    @ApiModelProperty(value = "分页大小")
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
