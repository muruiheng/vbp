package com.sgai.vbp.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
@Inherited
public @interface Column {
	
	/**
	 * column name
	 * @return
	 */
	@AliasFor("name")
	String value() default "";
	
	@AliasFor("value")
	String name() default "";
	/**
	 * 是否可为空
	 * @return
	 */
	boolean isNull() default true;
	
	/**
	 * 字段描述，作为验证使用
	 * @return
	 */
	String desc() default "";
}
