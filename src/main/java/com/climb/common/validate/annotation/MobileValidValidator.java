package com.climb.common.validate.annotation;

import cn.hutool.core.util.PhoneUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** 
 * @desc: 手机号码验证
 * @author: liqianlong
 * @date:  2020/10/16 10:03
 */ 
public class MobileValidValidator implements ConstraintValidator<MobileValid, String> {

    @Override
    public void initialize(MobileValid parameters) {
        javax.validation.constraints.Pattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        for ( javax.validation.constraints.Pattern.Flag flag : flags ) {
            intFlag = intFlag | flag.getValue();
        }
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        if (field == null || "".equals(field.trim())){
            return true;
        }
        return PhoneUtil.isMobile(field);
    }

}
