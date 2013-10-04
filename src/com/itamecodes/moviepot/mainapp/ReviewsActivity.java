package com.itamecodes.moviepot.mainapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.itamecodes.moviepot.R;

public class ReviewsActivity extends RTBaseActivity{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isNetworkAvailable(this)){
    		showAlert();
    	}else{
        // Override how this activity is animated into view
        // The new activity is pulled in from the left and the current activity is kept still
        // This has to be called before onCreate
        overridePendingTransition(R.anim.pullinfromleft, R.anim.hold);
         
        setContentView(R.layout.activity_reviews);
        String imdb=getIntent().getStringExtra("imdb");
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ReviewsFragment revfrag=ReviewsFragment.newInstance(imdb);
        Log.v("ra",imdb+"=imdb");
        ft.replace(R.id.reviefrag, revfrag);
        ft.commit();
        
    	}
    }
     
    @Override
    protected void onPause() {
        // Whenever this activity is paused (i.e. looses focus because another activity is started etc)
        // Override how this activity is animated out of view
        // The new activity is kept still and this activity is pushed out to the left
        overridePendingTransition(R.anim.hold, R.anim.pullouttoleft);
        super.onPause();
    }
}
