package com.ksoot.common.mongo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
// @Inherited
public @interface Auditable {

  String name() default "";

  String[] include() default {};

  String[] exclude() default {};
}
