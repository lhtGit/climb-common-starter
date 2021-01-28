package com.climb.common.bean;

import com.climb.common.constant.CommonConstant;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("API返回结果")
public  class Result<T> {

    @ApiModelProperty(value = "响应码")
    private int code;

    @ApiModelProperty(value = "响应消息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;

    @ApiModelProperty(value = "响应数组")
    private Collection<T> dataList;

    @ApiModelProperty(value = "是否成功")
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
