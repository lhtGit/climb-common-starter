package com.climb.common.validate.exception;

import com.alibaba.fastjson.JSON;
import com.climb.common.exception.GlobalException;
import com.climb.common.validate.vo.LocalConstraintViolation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lht
 * @date 2020/9/22 17:12
 */
public class ValidateError extends GlobalException {
    //连接符
    private final String joiner = ".";
    private final String direction = "->";
    @Getter
    //请求源
    private final Object target;
    @Getter
    //方法名称
    private final String methodName;

    @Getter
    //验证与数据源
    private final List<LocalConstraintViolation> constraintViolations;
    //验证消息集合
    @Getter
    private List<String> detailMessageList ;
    @Getter
    private List<String> simpleMessage;


    public ValidateError(Object target, String methodName, List<LocalConstraintViolation> constraintViolations) {
        this.target = target;
        this.methodName = methodName;
        this.constraintViolations = constraintViolations;
        initMessage();
    }

    /**
     * @Description: 初始化验证消息
     * @Author: lht
     * @Date: 2019/11/19 13:34
     */
    private void initMessage(){
        //完整消息
        detailMessageList = Lists.newArrayList();
        //简单的消息
        simpleMessage =  Lists.newArrayList();


        constraintViolations.forEach(localConstraintViolation->{
            //是否为对象
            if(localConstraintViolation.isObject()){
                StringBuilder msg = new StringBuilder();
                //指向类.方法.参数对象->
                msg.append(target.getClass().getSimpleName())
                        .append(joiner)
                        .append(methodName)
                        .append(joiner)
                        .append(localConstraintViolation.getObjectName())
                        .append(direction);
                //组装验证信息容器
                Map<String,String> errorObject = Maps.newHashMap();
                //遍历validate信息
                localConstraintViolation.getConstraintViolations().forEach(constraintViolation->{
                    //加入属性信息
                    errorObject.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
                    simpleMessage.add(constraintViolation.getMessage());
                });
                msg.append(JSON.toJSONString(errorObject));
                detailMessageList.add(msg.toString());
            }else{
                //指向类.参数->
                String targetName =target.getClass().getSimpleName()+joiner;

                localConstraintViolation.getConstraintViolations().forEach(constraintViolation->{
                    //加入属性信息
                    detailMessageList.add(targetName+constraintViolation.getPropertyPath().toString()+":"+constraintViolation.getMessage());
                    simpleMessage.add(constraintViolation.getMessage());
                });

            }
        });
    }
    @Override
    public String getMessage() {
        Iterator<String> it = simpleMessage.iterator();

        StringBuilder sb = new StringBuilder();
        for (;;) {
            String e = it.next();
            sb.append(e);
            if (!it.hasNext()){
                 break;
            }
            sb.append(',');
        }
        return sb.toString();
    }

}
