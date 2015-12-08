package com.han.myproject.test;


public class Test {
	public static void main(String[] args) {
			System.out.println(find(1232421412));
	}
	
	
	
	public static <T> T find(T t){
		System.out.println(t.getClass());
		System.out.println(t.hashCode());
		return t;
	}
	
	
}

