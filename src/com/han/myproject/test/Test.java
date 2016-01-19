package com.han.myproject.test;


public class Test {
	public static void main(String[] args) {
		String str = new String("amigoxiexiexingxing");
        System.out.println("原始：" + str);
        try {
			System.out.println("SHA后：" + SHAUtil.shaEncode(str));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static <T> T find(T t){
		System.out.println(t.getClass());
		System.out.println(t.hashCode());
		return t;
	}
	
	
}

