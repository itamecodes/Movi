package com.itamecodes.moviepot.mainapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.androidquery.util.AQUtility;

public abstract class RTBaseActivity extends FragmentActivity {
	
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	        getActionBar().setDisplayShowTitleEnabled(false);
	        
	        
	       
	    }
	 
	 public static boolean isNetworkAvailable(Context context) {
	     ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	     if (connectivity != null) {
	        NetworkInfo info = connectivity.getActiveNetworkInfo();
	        if (info != null) {
	          
	              if (info.getState() == NetworkInfo.State.CONNECTED) {
	                 return true;
	             
	           }
	        }
	     }
	     return false;
	  }
	 
	 
	 public void showAlert(){
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	 
				// set title
				alertDialogBuilder.setTitle("Network Not Available");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Set A Network Now!")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							//Intent intent = new Intent(Intent.ACTION_MAIN);
							Intent intent=new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							//intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
							startActivity(intent);
							RTBaseActivity.this.finish(); 
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							RTBaseActivity.this.finish();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
				}
	 
	 
	
		 /*
		 *@return boolean return true if the application can access the internet
		 */
	
	 
	@Override
	public void onStop(){
		super.onStop();
		
		       HttpResponseCache cache = HttpResponseCache.getInstalled();
		       if (cache != null) {
		           cache.flush();
		       }
		
	}
	

	
	@Override
	protected void onDestroy(){
        
        super.onDestroy();
        
        if(isTaskRoot()){

                //clean the file cache with advance option
                long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
                long targetSize = 2000000;      //remove the least recently used files until cache size is less than 2M
                AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
        }
	}
}
