package com.climb.common.config;

import com.climb.common.exception.GlobalException;
import com.google.common.collect.Lists;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日期非json格式转换
 * @author lht
 * @since 2020/11/16 17:53
 */
public class DateConverter implements Converter<String, Date> {
    private final ThreadLocal<List<SimpleDateFormat>> threadLocal = ThreadLocal.withInitial(() ->
            Lists.newArrayList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),new SimpleDateFormat("yyyy-MM-dd")));
    @Override
    public Date convert(String s) {
        List<SimpleDateFormat> simpleDateFormats = threadLocal.get();
        for (SimpleDateFormat simpleDateFormat : simpleDateFormats) {
            try {
                return simpleDateFormat.parse(s);
            }catch (Exception ignored){
            }
        }
        throw new GlobalException("时间格式化错误");
    }
}
