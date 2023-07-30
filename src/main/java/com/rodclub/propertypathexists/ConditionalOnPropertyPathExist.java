package com.rodclub.propertypathexists;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(OnPropertyPathExistsCondition.class)
public @interface ConditionalOnPropertyPathExist {
	@AliasFor("prefix")
	String value() default "";
	
	@AliasFor("value")
	String prefix() default "";
}
