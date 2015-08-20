package com.gmartinsribeiro.recyclerviewapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
public class Connectivity {
  
	/**
	 * Get the network info
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context){
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm.getActiveNetworkInfo();
	}
	
	/**
	 * Check if there is any connectivity
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context){
	    NetworkInfo info = Connectivity.getNetworkInfo(context);
	    return (info != null && info.isConnected());
	}

}