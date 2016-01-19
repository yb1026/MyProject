package com.han.myproject.web;

public class Html {
	
	public static final String BACKURL="http://172.18.137.63:8080/ACPSample_WuTiaoZhuan_Token/frontRcvResponse";
	
	
	public static String html() {
		return "<html>"
				+ "<head>"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>"
				+ "</head>"
				+ "<body>"
				+ "<form id = \"pay_form\" action=\"https://101.231.204.80:5000/gateway/api/frontTransReq.do\" method=\"post\">"
				+ "<input type=\"text\" name=\"txnType\" id=\"txnType\" value=\"79\"/>"
				+ "<input type=\"text\" name=\"frontUrl\" id=\"frontUrl\" value=\"http://172.18.137.63:8080/ACPSample_WuTiaoZhuan_Token/frontRcvResponse\"/>"
				+ "<input type=\"text\" name=\"channelType\" id=\"channelType\" value=\"07\"/>"
				+ "<input type=\"text\" name=\"merId\" id=\"merId\" value=\"027430592220003\"/>"
				+ "<input type=\"text\" name=\"tokenPayData\" id=\"tokenPayData\" value=\"{trId=62000000001&tokenType=01}\"/>"
				+ "<input type=\"text\" name=\"reserved\" id=\"reserved\" value=\"{cardNumberLock=1}\"/>"
				+ "<input type=\"text\" name=\"txnSubType\" id=\"txnSubType\" value=\"00\"/>"
				+ "<input type=\"text\" name=\"version\" id=\"version\" value=\"5.0.0\"/>"
				+ "<input type=\"text\" name=\"accType\" id=\"accType\" value=\"01\"/>"
				+ "<input type=\"text\" name=\"accNo\" id=\"accNo\" value=\"6226090000000048\"/>"
				+ "<input type=\"text\" name=\"signMethod\" id=\"signMethod\" value=\"01\"/>"
				+ "<input type=\"text\" name=\"backUrl\" id=\"backUrl\" value=\"http://222.222.222.222:8080/ACPSample_WuTiaoZhuan_Token/BackRcvResponse\"/>"
				+ "<input type=\"text\" name=\"certId\" id=\"certId\" value=\"40220995861346480087409489142384722381\"/>"
				+ "<input type=\"text\" name=\"encoding\" id=\"encoding\" value=\"UTF-8\"/>"
				+ "<input type=\"text\" name=\"bizType\" id=\"bizType\" value=\"000902\"/>"
				+ "<input type=\"text\" name=\"reqReserved\" id=\"reqReserved\" value=\"透传字段\"/>"
				+ "<input type=\"text\" name=\"signature\" id=\"signature\" value=\"UbMmCmVnvurYDYCv06b47YK6AI2qApttqfL+9nb2MLPp2G7pJVhTACPlf7rBIlgaEHivRSriKx3S63KKJAmppYVBmMFYTEQwbojEUeMT6SYls2I/hhCXnS/fISlxUDDAP2NiKRMJOCVNvdrt9M2II4b7aibI4Ti5oX9F43XIhL0=\"/>"
				+ "<input type=\"text\" name=\"orderId\" id=\"orderId\" value=\"20151223111723\"/>"
				+ "<input type=\"text\" name=\"txnTime\" id=\"txnTime\" value=\"20151223111723\"/>"
				+ "<input type=\"text\" name=\"accessType\" id=\"accessType\" value=\"0\"/>"
				+ "</form>"
				+ "</body>"
				+ "<script type=\"text/javascript\">document.all.pay_form.submit();</script>"
				+ "</html>";
	}
	
	
	public static String replaceHtml(){
		String html = html();
		return html.replace("", "")
				.replace("", "")
				.replace("", "")
				.replace("", "");
	}
}
