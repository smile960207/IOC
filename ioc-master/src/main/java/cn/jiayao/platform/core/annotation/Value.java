package cn.jiayao.platform.core.annotation;

import java.lang.annotation.*;

/**
 * 类 名: Value
 * 描 述: 获取配置文件中的键值对
 * 作 者: 黄加耀
 * 创 建: 2019/3/17 : 11:20
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Value {

    String value() default "";

}
