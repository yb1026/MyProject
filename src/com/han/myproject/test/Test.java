package com.han.myproject.test;

import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		// Pattern p =
		// Pattern.compile("^((073[0-9])|(074[3-6]))([2-9])(\\d{6})(\\d?)$");
		// System.out.println(p.matcher("074382562185").matches());
		int x =0;
		
		for (int i = 234; i < 987; i++) {
			for (int k = 234; k < 987; k++) {
				if(getXy(i,k)){
					x++;
				}
			}
		}
		
		
		System.out.println(x);
	}
	
	
	private static boolean getXy(int i,int k){
		int o = i+k;
		
		String arrStr = "0123456789";
		String[]  arr = new String[arrStr.length()];
		
		for(int s=0;s<arr.length;s++){
			arr[s] = String.valueOf(arrStr.charAt(s));
		}
	
		if (o>=1000&&o<=1876) {
			String ostr = String.valueOf(o);
			String istr = String.valueOf(i);
			String kstr = String.valueOf(k);
			for (int n=0; n<arr.length;n++) {
				String str =arr[n];
				if (ostr.indexOf(str) >= 0) {
					arrStr = arrStr.replace(str, "");
				} else if (istr.indexOf(str) >= 0) {
					arrStr = arrStr.replace(str, "");
				} else if (kstr.indexOf(str) >= 0) {
					arrStr = arrStr.replace(str, "");
				}
			}
			
			if(arrStr.length()==0){
				System.err.print("x="+i);
				System.err.print(" y="+k);
				System.err.println(" 结果="+o);
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	

	public static <T> T find(T t) {
		System.out.println(t.getClass());
		System.out.println(t.hashCode());
		return t;
	}

}
