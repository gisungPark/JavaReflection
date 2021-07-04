package com.cos.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
// Runtime 시점에서 annotation이 동장한다.
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value();
}
