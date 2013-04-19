package com.eiri.wifidb_uploader;

import java.util.List;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.util.Log;


public class WiFiScanReceiver extends BroadcastReceiver {
  private static final String TAG = "WiFiDB_WiFiScanReceiver";
  ScanService ScanService;

  public WiFiScanReceiver(ScanService ScanService) {
    super();
    this.ScanService = ScanService;
  }

  @Override
  public void onReceive(Context c, Intent intent) {
	  
    List<ScanResult> results = ScanService.wifi.getScanResults();

    for (ScanResult result : results) {
    	Log.d(TAG, "onReceive() message:1");
    	WifiDB post = new WifiDB();
    	GPS gps = new GPS(c);
    	if(!gps.canGetLocation())
    	{
    		gps.showSettingsAlert();
    	}
	    Location location = gps.getLocation();
	    
	    double latitude_str = location.getLatitude();
	    double longitude_str = location.getLongitude();
	    Log.d(TAG, "LAT: " + latitude_str + "LONG: " + longitude_str);
    	post.postLiveData(result.SSID, result.BSSID, result.capabilities, result.frequency, result.level, latitude_str, longitude_str);
    }

  }

public static String getTag() {
	return TAG;
}
}