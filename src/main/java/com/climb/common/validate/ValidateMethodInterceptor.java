package com.climb.common.validate;

import com.climb.common.validate.exception.ValidateError;
import com.climb.common.validate.vo.LocalConstraintViolation;
import com.google.common.collect.Lists;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * validate 拦截器
 * @author lht
 * @since  2020/12/23 17:47
 */
public class ValidateMethodInterceptor implements MethodInterceptor {
    private final Validator validator;

    public ValidateMethodInterceptor() {
        this(Validation.buildDefaultValidatorFactory());
    }

    public ValidateMethodInterceptor(ValidatorFactory validatorFactory) {
        this(validatorFactory.getValidator());
    }

    public ValidateMethodInterceptor(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (this.isFactoryBeanMetadataMethod(invocation.getMethod())) {
            return invocation.proceed();
        } else {
            Class<?>[] groups = this.determineValidationGroups(invocation);
            ExecutableValidator execVal = this.validator.forExecutables();
            Method methodToValidate = invocation.getMethod();
            Object target = invocation.getThis();
            Set<ConstraintViolation<Object>> result;

            List<LocalConstraintViolation> constraintViolations = Lists.newArrayList();

            try {
                //先验证方法
                result = execVal.validateParameters(target, methodToValidate, invocation.getArguments(), groups);

            } catch (IllegalArgumentException var7) {
                methodToValidate = BridgeMethodResolver.findBridgedMethod(ClassUtils.getMostSpecificMethod(invocation.getMethod(), target.getClass()));
                result = execVal.validateParameters(target, methodToValidate, invocation.getArguments(), groups);
            }


            if (!result.isEmpty()) {
                constraintViolations.add(new LocalConstraintViolation(result));
                throw new ValidateError(target,methodToValidate.getName(),constraintViolations);
            } else {
//                validateParams(Object[] arguments,Class<?>[] group,Object target,List<ValidateError.LocalConstraintViolation> constraintViolations){
                //验证参数值
                validateParams(invocation.getArguments(), groups,target,constraintViolations);

                //是否存在错误
                if (!constraintViolations.isEmpty()) {
                    throw new ValidateError(target,methodToValidate.getName(),constraintViolations);
                }

                Object returnValue = invocation.proceed();
                //返回值验证
                result = execVal.validateReturnValue(target, methodToValidate, returnValue, groups);
                //是否存在错误
                if (!result.isEmpty()) {
                    constraintViolations.add(new LocalConstraintViolation(result));
                    throw new ValidateError(target,methodToValidate.getName(),constraintViolations);
                } else {
                    return returnValue;
                }
            }
        }
    }

    /**
     * @Description: 是否为源方法
     * @Author: lht
     * @Date: 2019/11/19 10:19
     */
    private boolean isFactoryBeanMetadataMethod(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        if (clazz.isInterface()) {
            return (clazz == FactoryBean.class || clazz == SmartFactoryBean.class) && !method.getName().equals("getObject");
        } else {
            Class<?> factoryBeanType = null;
            if (SmartFactoryBean.class.isAssignableFrom(clazz)) {
                factoryBeanType = SmartFactoryBean.class;
            } else if (FactoryBean.class.isAssignableFrom(clazz)) {
                factoryBeanType = FactoryBean.class;
            }

            return factoryBeanType != null && !method.getName().equals("getObject") && ClassUtils.hasMethod(factoryBeanType, method.getName(), method.getParameterTypes());
        }
    }

    /**
     * @Description: 获取validate组
     * @Author: lht
     * @Date: 2019/11/19 10:19
     */
    private Class<?>[] determineValidationGroups(MethodInvocation invocation) {
        Validated validatedAnn = (Validated) AnnotationUtils.findAnnotation(invocation.getMethod(), Validated.class);
        if (validatedAnn == null) {
            validatedAnn = (Validated)AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Validated.class);
        }

        return validatedAnn != null ? validatedAnn.value() : new Class[0];
    }

    /**
     * @Description: 验证参数数组对象
     * @Author: lht
     * @Date: 2019/11/19 10:20
     */
    private void  validateParams(Object[] arguments,Class<?>[] group,Object target,List<LocalConstraintViolation> constraintViolations){
        for(Object param : arguments){
            if(param !=null && !isJavaClass(param.getClass())){
                Set<ConstraintViolation<Object>> constraintViolations1 = validator.validate(param,group);
                if(constraintViolations1!=null && constraintViolations1.size()>0){
                    constraintViolations.add(new LocalConstraintViolation(constraintViolations1,param.getClass().getSimpleName()));
                }
            }
        }
    }


    /**
     * @Description: 判断一个类是JAVA类型还是用户定义类型
     * @Author: lht
     * @Date: 2019/11/18 22:10
     */
    private boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }



}

