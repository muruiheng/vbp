package com.sgai.vbp.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类
 * @author mrh 2018年3月19日
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface Entity {
	/**
	 * 默认情况下为表名 与@table 意义相同
	 * @return
	 */
	String value() default "";
	
	/**
	 * 表名 与@value 意义相同
	 * @return
	 */
	String table() default "";
}
