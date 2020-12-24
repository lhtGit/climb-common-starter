package com.climb.common.validate;

import cn.hutool.core.lang.Assert;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.executable.ExecutableValidator;
import java.lang.annotation.Annotation;

/**
 * 拦截器 Advisor
 * @author lht
 * @date 2020/9/22 17:06
 */
@ConditionalOnClass(ExecutableValidator.class)
public class ValidateAutoAdvisor extends AbstractPointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return buildPointcut(Validated.class);
    }

    @Override
    public Advice getAdvice() {
        return new ValidateMethodInterceptor();
    }

    private Pointcut buildPointcut(Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, "'annotationTypes' must not be null");
        //类级别
        Pointcut cpc = new AnnotationMatchingPointcut(annotationType, true);
        //方法级别
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(annotationType);
        //对于类和方法上都可以添加注解的情况
        //类上的注解,最终会将注解绑定到每个方法上
        ComposablePointcut result = new ComposablePointcut(cpc);
        return result.union(mpc);
    }


}
