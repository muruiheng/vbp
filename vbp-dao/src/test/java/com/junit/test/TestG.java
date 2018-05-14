package com.junit.test;

import org.junit.Test;

public class TestG extends ClassA<User>{



	@Test
	public void testG() {
		try {
			TestG test = new TestG();
			User obj = new User();
			obj.setPwd("pwd1");
			obj.setUserName("userName1");
			test.setObject(obj);
			test.testClassA();
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
