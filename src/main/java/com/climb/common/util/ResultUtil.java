package com.climb.common.util;


import com.climb.common.bean.PageDefinition;
import com.climb.common.bean.PageResult;
import com.climb.common.bean.Result;
import com.climb.common.constant.CommonConstant;
import com.climb.common.exception.ErrorMessage;

import java.util.List;
import java.util.Set;

/*
 * 统一返回值工具类
 * @Author lht
 * @Date  2020/9/14 09:37
 */
public class ResultUtil {

    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(CommonConstant.SUCCESS_MSG);
        return result;
    }
    public static <T> Result<T> success(T data){
        return success(CommonConstant.SUCCESS_MSG,data);
    }
    public static <T> Result<T> success(List<T> dataList){
        return success(CommonConstant.SUCCESS_MSG,dataList);
    }
    public static <T> Result<T> success(String msg,List<T> dataList){
        Result<T> result = new Result<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(msg);
        result.setDataList(dataList);
        return result;
    }
    public static <T> Result<T> success(Set<T> dataList){
        return success(CommonConstant.SUCCESS_MSG,dataList);
    }
    public static <T> Result<T> success(String msg,Set<T> dataList){
        Result<T> result = new Result<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(msg);
        result.setDataList(dataList);
        return result;
    }


    public static <T> Result<T> success(String msg,T data){
        Result<T> result = new Result<>();
        result.setCode(CommonConstant.SUCCESS_CODE);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> PageResult<T> successPage(String msg, long page, long size, long total, List<T> data){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCode(CommonConstant.SUCCESS_CODE);
        pageResult.setMsg(msg);
        pageResult.setDataList(data);

        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(total);
        return pageResult;
    }

    public static <T> PageResult<T> successPage(long page,long size,long total,List<T> data){
        return successPage(CommonConstant.SUCCESS_MSG,page,size,total,data);
    }


    public static <T> Result<T> error(){
        return error(CommonConstant.ERROR_MSG);
    }

    public static <T> Result<T> error(T data){
        Result<T> result = new Result<>();
        result.setCode(CommonConstant.ERROR_CODE);
        result.setMsg(CommonConstant.ERROR_MSG);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.setCode(CommonConstant.ERROR_CODE);
        result.setMsg(msg);
        return result;
    }
    public static <T> Result<T> error(String msg,int code){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static <T> Result<T> error(ErrorMessage errorMessage){
        Result<T> result = new Result<>();
        result.setCode(errorMessage.getCode());
        result.setMsg(errorMessage.getMsg());
        return result;
    }
    public static <T> Result<T> error(ErrorMessage errorMessage, T date){
        Result<T> result = new Result<>();
        result.setCode(errorMessage.getCode());
        result.setMsg(errorMessage.getMsg());
        result.setData(date);
        return result;
    }

    public static <T> PageResult<T> errorPage(String msg){
        PageResult<T> result = new PageResult<>();
        result.setCode(CommonConstant.ERROR_CODE);
        result.setMsg(msg);
        return result;
    }


    public static <T> PageResult<T> successPage(PageDefinition<?> page, List<T> data){
        return successPage(CommonConstant.SUCCESS_MSG,page.getCurrent(),page.getSize(),page.getTotal(),data);
    }


    public static <T> PageResult<T> successPage(PageDefinition<T> page){
        return successPage(CommonConstant.SUCCESS_MSG,page.getCurrent(),page.getSize(),page.getTotal(),page.getRecords());
    }
    public static <T> PageResult<T> successPage(String msg,PageDefinition<T> page){
        return successPage(msg,page.getCurrent(),page.getSize(),page.getTotal(),page.getRecords());
    }

}
