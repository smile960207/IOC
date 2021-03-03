package cn.jiayao.platform.core.annotation;

import java.lang.annotation.*;

/**
 * 类 名: MyAutowired
 * 描 述: 注入注解--将需要交给IOC容器管理的类放置 -- 定义在属性上的
 * 作 者: 黄加耀
 * 创 建: 2019/3/17 : 8:55
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface MyAutowired {

    String value() default "";

}
