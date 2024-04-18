package org.nott.cli.common.aop;

import java.lang.annotation.*;

/**
 * log annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
public @interface AopLog {

    String value() default "";

    boolean isSave() default false;
}
