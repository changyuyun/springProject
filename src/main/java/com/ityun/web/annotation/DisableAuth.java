package com.ityun.web.annotation;

import java.lang.annotation.*;

/**
 * 非鉴权注解，Controller使用此注解，过滤器将不拦截
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface DisableAuth {

}
