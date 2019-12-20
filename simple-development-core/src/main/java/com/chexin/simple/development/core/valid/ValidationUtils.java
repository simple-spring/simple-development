package com.chexin.simple.development.core.valid;

import com.chexin.simple.development.core.utils.DateUtils;
import com.chexin.simple.development.support.GlobalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.chexin.simple.development.support.ResponseCode.RES_PARAM_INVALID;
import static com.chexin.simple.development.support.ResponseCode.RES_PARAM_IS_EMPTY;


/**
 * @author liko
 * @Date 2019-10-02 13:50
 * @DESCRIPTION 参数校验
 */
public class ValidationUtils {
    private static final Logger logger = LogManager.getLogger(ValidationUtils.class);

    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    public static <T> void validate(T... obj) {
        if (obj == null) {
            return;
        }
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数为空");
            }
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj[i]);
            if (constraintViolations.size() <= 0) {
                continue;
            } else {
                String message = constraintViolations.iterator().next().getMessage();
                logger.error(DateUtils.getCurrentTime() + "param invalidate fail :{}" + message);
                throw new GlobalException(RES_PARAM_INVALID, message);
            }
        }
    }

}