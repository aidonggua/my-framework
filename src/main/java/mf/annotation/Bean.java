package mf.annotation;

import java.lang.annotation.*;

/**
 * Bean 注解
 *
 * @author yehao
 * @date 2020/4/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {

    /**
     * bean id
     *
     * @author yehao
     * @date 2020/4/13
     */
    String value() default "";
}
