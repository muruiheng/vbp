package com.junit.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import com.sgai.vbp.util.AssertUtil;

public class ClassA<T>{
	private T obj;         
    public void setObject(T obj) {      this.obj = obj;  }       
    public T getObject() {    return obj;   }     
      
    /** 
     * 获取T的实际类型 
     * @throws ClassNotFoundException 
     */  
    public void testClassA() throws NoSuchFieldException, SecurityException, ClassNotFoundException {  
        System.out.print("getSuperclass:");  
        System.out.println(this.getClass().getSuperclass().getName());  
        System.out.print("getGenericSuperclass:");  
        Type t = this.getClass().getGenericSuperclass();  
        System.out.println(t);  
        if (ParameterizedType.class.isAssignableFrom(t.getClass())) {  
            for (Type t1 : ((ParameterizedType) t).getActualTypeArguments()) {  
            	 System.out.println("getActualTypeArguments:" + t1);  
            	getALLFields(Class.forName(t1.getTypeName()));
            }  
            System.out.println();  
        } 
        
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
        	if (!field.isAccessible()) {
        		Method method = this.getterMethod(field.getName(), obj.getClass());
        	
        		try {
        			if (AssertUtil.isVal(method)) {
        				String message = (String) method.invoke(obj);
    					System.out.println(message);
        			}
					
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
        	}
        }
    }  
    
    private Method getterMethod(String fieldName, Class<?> clazz) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		// 获得和属性对应的setXXX()/setXXX()方法的名字
		String getMethodName = "get" + firstLetter + fieldName.substring(1);
		try {
			Method getMethod = clazz.getMethod(getMethodName, new Class<?>[] {});
			return getMethod;
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
		return null;
	}
    
    private Set<String> getALLFields(Class<?> genericType) {
		Field[] declaredFields = genericType.getDeclaredFields();
		Set<String> fieldSet = new HashSet<>();
		for (Field field : declaredFields) {
			if (!field.isAccessible() && !"serialVersionUID".equals(field.getName())) {
				fieldSet.add(field.getName());
				System.out.println(field.getName());
			}
		}
		return fieldSet;
	}
}
