package com.cultivator.myproject.common.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 基于网络定位服务
 * 
 * @author yb1026
 * 
 */
public class SimpleLocationService {


	private static SimpleLocationService simpleLocationService;

	public static SimpleLocationService getInstance(){
		if(simpleLocationService==null){
			simpleLocationService = new SimpleLocationService();
		}
		return simpleLocationService;
	}




	public static interface Callback {
		void callback();
	}

	private Callback callback;

	private static final int UPDATE_TIME = 1 * 60 * 5000;

	//private static final String CHINA_COUNTRY_CODE = "0";
	// private static final boolean checkcode = true;

	private LocationClient mLocationClient;

	private MLocation location;

	// private LocationManager locationManager;

	public void init(Context ctx) {
		if (mLocationClient == null) {
			LocationListenner myListener = new LocationListenner();
			mLocationClient = new LocationClient(ctx);
			mLocationClient.registerLocationListener(myListener);
		}
		setLocationOption();
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}

	}

	public void requestLocation(Context ctx, Callback callback) {

		init(ctx);

		this.callback = callback;

		mLocationClient.requestLocation();

	}

	public void requestLocation(Context ctx) {

		init(ctx);

		mLocationClient.requestLocation();

	}

	public void unRegisterLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}

	public class LocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation bdLocation) {

			if (bdLocation == null || (bdLocation.getLatitude() < 0.00000000001 && bdLocation.getLatitude() > 0)
					|| (bdLocation.getLatitude() < -0.00000000001 && bdLocation.getLatitude() < 0)
					|| (bdLocation.getLongitude() < 0.00000000001 && bdLocation.getLongitude() > 0)
					|| (bdLocation.getLongitude() > -0.00000000001 && bdLocation.getLongitude() < 0)) {
				mLocationClient.requestLocation();

			} else {
				String address = bdLocation.getAddrStr();
				address = address == null ? "" : address;
				// String countryCode = bdLocation.getCountryCode();
				// // 限制定位地址只能在中国(不含港澳台地区), 其他地址返回定位信息为空
				// if (CHINA_COUNTRY_CODE.equals(countryCode)
				// && address.indexOf("香港") < 0
				// && address.indexOf("澳门") < 0
				// && address.indexOf("台湾") < 0) {

				if (location == null) {
					location = new MLocation();
				}
				location.setAddress(bdLocation.getAddrStr());
				location.setSpecLatitude(bdLocation.getLatitude());
				location.setSpecLongitude(bdLocation.getLongitude());
				
				location.setCity(bdLocation.getCity());
				location.setCountry(bdLocation.getDistrict());
				location.setTown("");
				location.setVillage(bdLocation.getStreet());
				
				
				unRegisterLocation();
				// }

				/*
				 * if
				 * ((!checkcode)||countryCode.equals(bdLocation.getCountryCode()
				 * )) {
				 * 
				 * if(location==null){ location = new TiLocation(); }
				 * location.setAddress(bdLocation.getAddrStr());
				 * location.setSpecLatitude(bdLocation.getLatitude());
				 * location.setSpecLongitude(bdLocation.getLongitude());
				 * unRegisterLocation(); }else {
				 * mLocationClient.requestLocation(); }
				 */

				if (callback != null) {
					callback.callback();
				}
			}
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationMode.Battery_Saving);
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedAddress(true);
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setScanSpan(UPDATE_TIME);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 是否已获取过地址
	 * 
	 * @return
	 */
	public boolean hasLocation() {

		if (location == null) {
			return false;
		}

		return true;
	}

	public MLocation getLocation() {
		return location;
	}

}
