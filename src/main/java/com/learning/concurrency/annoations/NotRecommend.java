package com.learning.concurrency.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LR
 * @Descriprition: 声明注解
 *                  用来标记线程[不推荐]的类或写法
 * @Date: Created in 20:22 2019/11/26
 * @Modified By:
 **/
@Target(ElementType.TYPE)  // 注解作用的目标
@Retention(RetentionPolicy.SOURCE) // 注解存在的范围
public @interface NotRecommend {
    String value() default "";
}
