// Vadym Volokitin 

package org.godotengine.godot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;
import android.provider.Settings;
import android.graphics.Color;
import android.util.Log;
import java.util.Locale;
import android.view.Gravity;
import android.view.View;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;


public class GodotGpsModule extends Godot.SingletonBase {

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            latitude        = loc.getLatitude();
            longitude       = loc.getLongitude();
            altitude        = loc.getAltitude();
            gps_time        = loc.getTime();
            gps_speed       = loc.getSpeed();
            gps_bearing     = loc.getBearing();
            gps_accuracy    = loc.getAccuracy();
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }

    // The minimum distance to change Updates in meters // 1 meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; 

    // The minimum time between updates in milliseconds // 1 sec
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1 * 1;     

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    // Location
    Location location; 
    
    // Latitude
    private double latitude = 0; 
    
    // Longitude
    private double longitude = 0; 
    
    // Altitude
    private double altitude = 0;     
    
    // Time
    private long gps_time = 0;    
    
    // Speed
    private float gps_speed = 0;    
   
    // Bearing
    private float gps_bearing = 0;       

    // Accuracy
    private float gps_accuracy = 0;     
    
    // The main activity of the game
	private Activity activity = null; 
    
    //LocationListener locationListener;
    LocationListener locationListener = new MyLocationListener();
    
    // Declaring a Location Manager
    protected LocationManager locationManager;
    

	/* Init
	 * ********************************************************************** */

    /**
     * Function to get latitude
     */
    
    public String getStringLatitude() {
        String value =  Double.toString(this.latitude);
        
        return(value);
    } 

    
    /**
     * Function to get longitude
     * */ 
   
    public String getStringLongitude() {
        String value = Double.toString(this.longitude);
        return(value);
    } 


    /**
     * Function to get altitude
     * */ 
   
    public String getStringAltitude() {
        String value = Double.toString(this.altitude);
        return(value);
    } 

    
    /**
     * Function to get GPS Time
     * */ 
   
    public String getStringGPSTime() {
        String value = Long.toString(this.gps_time);
        return(value);
    } 

    
    /**
     * Function to get GPS Speed
     * */ 
   
    public String getStringGPSSpeed() {
        String value = Float.toString(this.gps_speed);
        return(value);
    } 
    

    /**
     * Function to get GPS Bearing
     * */ 
   
    public String getStringGPSBearing() {
        String value = Float.toString(this.gps_bearing);
        return(value);
    } 


    /**
     * Function to get GPS Accuracy
     * */ 
   
    public String getStringGPSAccuracy() {
        String value = Float.toString(this.gps_accuracy);
        return(value);
    } 

    
    public String getGPSState() {
        int value = 0;
        if (this.isGPSEnabled){
            value = 1;
        }
        return (Integer.toString(value));
    } 

    
    public String getNetworkState() {
        int value = 0;
        if (this.isNetworkEnabled){
            value = 1;
        }
        return (Integer.toString(value));
    } 

    public void getInit() {/********************************************************************************************/
		activity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
                try {
                        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        
                        // Getting GPS status
                        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                        // Getting network status
                        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                        if (locationManager != null) 
                        {
                            canGetLocation = true;
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) 
                            {
                                latitude =  location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        } 
                        
                        locationManager.requestLocationUpdates(
                                                                LocationManager.GPS_PROVIDER,
                                                                MIN_TIME_BW_UPDATES,
                                                                MIN_DISTANCE_CHANGE_FOR_UPDATES, 
                                                                locationListener
                                                             );        
                }
                catch (Exception e) 
                {
                    //e.printStackTrace();
                    //Log.w("w",e);
                }                
			}
		});            
            
    }


    
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
	public void stopUsingGPS()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override public void run()
			{
                if(locationManager != null){
                    locationManager.removeUpdates(locationListener);
                }
			}
		});
	}    
    
    
	/* Utils
	 * ********************************************************************** */

	/**
	 * Generate MD5 for the deviceID
	 * @param String s The string to generate de MD5
	 * @return String The MD5 generated
	 */
	private String md5(final String s)
	{
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i=0; i<messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2) h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();
		} catch(NoSuchAlgorithmException e) {
			//Logger.logStackTrace(TAG,e);
		}
		return "";
	}

	/**
	 * Get the Device ID for GpsModule
	 * @return String Device ID
	 */
	private String getGpsModuleDeviceId()
	{
		String android_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
		String deviceId = md5(android_id).toUpperCase(Locale.US);
		return deviceId;
	}

	/* Definitions
	 * ********************************************************************** */

	/**
	 * Initilization Singleton
	 * @param Activity The main activity
	 */
 	static public Godot.SingletonBase initialize(Activity activity)
 	{
 		return new GodotGpsModule(activity);
 	}

	/**
	 * Constructor
	 * @param Activity Main activity
	 */
	public GodotGpsModule(Activity p_activity) {
		registerClass("GpsModule", new String[] {
			"init",
            "getInit",
            "getGPSState",
            "getNetworkState",
            "getStringLatitude",
            "getStringLongitude",
            "getStringAltitude",
            "getStringGPSTime",
            "getStringGPSSpeed",
            "getStringGPSBearing",
            "getStringGPSAccuracy",
			"getLocation", 
            "stopUsingGPS"
		});
		activity = p_activity;
	}
}
