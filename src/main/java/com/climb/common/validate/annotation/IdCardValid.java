package com.climb.common.validate.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/** 
 * @desc: 身份证号验证
 * @author: liqianlong
 * @date:  2020/10/16 9:55
 */ 
@Documented
@Constraint(validatedBy = IdCardValidValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCardValid {

    Pattern.Flag[] flags() default {};

    String message() default "身份证号格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
