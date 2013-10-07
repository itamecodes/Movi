package com.itamecodes.moviepot.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.GridClickedObject;
import com.squareup.otto.Subscribe;

public class SearchActivity extends BaseActivity {
	public final String TAG="SearchActivity";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isNetworkAvailable(this)){
    		showAlert();
    	}else{
		setContentView(R.layout.similarlayout);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		SearchMovieFragment smF = SearchMovieFragment
				.newInstance(getIntent().getStringExtra("thequerystring"));
		ft.replace(R.id.thesimilarmovie, smF);
		ft.addToBackStack(null);
		ft.commit();
    	}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	
	 @Override
	 public void onResume() {
	        super.onResume();
	        EventBus.getInstance().register(this);
	  }
	    
	 @Override
	 public void onPause() {
		 EventBus.getInstance().unregister(this);
	        super.onPause();
	       
	      }
	 
	  @Subscribe
	    public void onGridItemClicked(GridClickedObject goc){
	      String themovieidclicked=goc.getId();
	    	
	    	Intent detailIntent = new Intent(this, MovieDetailActivityLatest.class);
	        detailIntent.putExtra(MovieDetailFragment.MOVIE_ID, themovieidclicked);
	        startActivity(detailIntent);
	      
	    }
}
