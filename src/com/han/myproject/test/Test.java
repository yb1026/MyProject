package com.han.myproject.test;

import android.util.Log;



public class Test {
	public static void main(String[] args) {
		String userName = "13873103362";
		String sKey = userName + userName.substring(userName.length()-5);
		String encLoginPwd = "";
		try {
			encLoginPwd = EncryptUtil.encrypt("123456", sKey);
			Log.e("password", encLoginPwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
}
