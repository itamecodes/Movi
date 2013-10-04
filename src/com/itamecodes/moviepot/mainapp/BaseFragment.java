package com.itamecodes.moviepot.mainapp;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.util.Log;


public abstract class BaseFragment extends Fragment {
    
	@Override
	public void onPause(){
        
        super.onPause();
        
        
        		
        
        
}
	public static String formatDate(String datein){
		Date date;
		try {
			date=new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(datein);
			String newdate=new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH).format(date);
			return newdate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	
	

}
