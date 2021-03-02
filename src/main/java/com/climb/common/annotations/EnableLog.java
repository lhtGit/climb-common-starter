package com.climb.common.annotations;

import com.climb.common.handler.LogInterceptor;
import com.climb.common.request.filter.RequestWrapperFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启自定义log日志
 * 会打印所有请求的参数、结果是否成功、消耗时间等
 * @author lht
 * @since  2021/3/2 15:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({LogInterceptor.class, RequestWrapperFilter.class})
public @interface EnableLog {
}
