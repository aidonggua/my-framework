package mf.annotation;

import java.lang.annotation.*;

/**
 * 配置包扫描注解
 *
 * @author yehao
 * @date 2020/4/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanScan {

    /**
     * 包扫描路径
     *
     * @author yehao
     * @date 2020/4/13
     */
    String value();
}
