package net.devtech.test.cursed;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestInvoke implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		return 2;
	}
}
