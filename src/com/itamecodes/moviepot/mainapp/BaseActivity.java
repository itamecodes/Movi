package com.itamecodes.moviepot.mainapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.androidquery.util.AQUtility;
import com.itamecodes.moviepot.R;

public abstract class BaseActivity extends FragmentActivity implements OnQueryTextListener{
	
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	        getActionBar().setDisplayShowTitleEnabled(false);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	        
	        
	       
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
							BaseActivity.this.finish(); 
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							BaseActivity.this.finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        try{
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setQueryHint(getResources().getString(R.string.search_title));
            searchView.setOnQueryTextListener(this);
           /* int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            // Getting the 'search_plate' LinearLayout.
            View searchPlate = searchView.findViewById(searchPlateId);
            // Setting background of 'search_plate' to earlier defined drawable.            
            searchPlate.setBackgroundResource(R.drawable.searchview); */         
            int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView textView = (TextView) searchView.findViewById(id);
            textView.setTextColor(Color.WHITE);
            
            }catch(Exception e){
            	Log.v("viveksearchquery","inflated"+e.getMessage()+"--"+e.getClass()+"-"+e.getClass());
            }
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:{
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this,
					new Intent(this, MainActivity.class));
			return true;
			}
			case R.id.about:{
				Intent intent=new Intent(this,AboutActivity.class);
				startActivity(intent);
				return true;
			}
		}
		return false;
	}
	
    @Override
	public boolean onQueryTextChange(String newText) {
		
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		String thequerystring=query;
		Log.v("viveksearchquery",thequerystring);
		Intent detailIntent = new Intent(this, SearchActivity.class);
        detailIntent.putExtra("thequerystring", thequerystring);
        startActivity(detailIntent);
    	/*
		SearchMovieFragment mDf = (SearchMovieFragment) getSupportFragmentManager().findFragmentByTag("searchmoviesfrag");
    	if(mDf==null || mDf.getMovieQuery()!=thequerystring){
    		

    	}*/
		return true;
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
