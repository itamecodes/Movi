package com.itamecodes.moviepot.mainapp;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.GridClickedObject;
import com.itamecodes.moviepot.eventobjects.ReviewsNeeded;
import com.itamecodes.moviepot.mainapp.CelebrityDetailActivity.SectionsPagerAdapter;
import com.itamecodes.moviepot.utils.CelebrityItemClicked;
import com.itamecodes.moviepot.utils.TrailerNeeded;
import com.squareup.otto.Subscribe;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

public class MovieDetailActivityLatest extends BaseActivity implements ActionBar.TabListener {
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	ViewPager mViewPager;
	static final String TAG="MovieDetailloader";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isNetworkAvailable(this)){
    		showAlert();
    	}else{
		setContentView(R.layout.detailviewpager);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.moviedetailpager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
    	}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0: {
				MovieDetailFragment fragment = MovieDetailFragment
						.newInstance(getIntent().getStringExtra(
								MovieDetailFragment.MOVIE_ID));
				return fragment;
			}
			case 1: {
				
				MovieCastFragment fragment = MovieCastFragment
						.newInstance(getIntent().getStringExtra(
								MovieDetailFragment.MOVIE_ID));
				return fragment;
			}
			case 2: {
				
				SimilarMoviesFragment fragment = SimilarMoviesFragment
						.newInstance(getIntent().getStringExtra(
								MovieDetailFragment.MOVIE_ID));
				return fragment;
			}
			

			}

			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.movie_detail_activity).toUpperCase();
			case 1:
				return getString(R.string.cast).toUpperCase();
			case 2:
				return getString(R.string.viewsimil).toUpperCase();
			
			}
			return null;
		}
	}
	
	
	 @Subscribe
	    public void onGridItemClicked(GridClickedObject goc){
		 Log.v("viveksimilar",""+goc.getId());
	     String themovieidclicked=goc.getId();
	     Intent detailIntent = new Intent(this, MovieDetailActivityLatest.class);
	     detailIntent.putExtra(MovieDetailFragment.MOVIE_ID, themovieidclicked);
	     startActivity(detailIntent);
	      
	    }
	 @Subscribe
	 public void onCelebrityItemClicked(CelebrityItemClicked goc){
	    	String thecelebidclicked=goc.getItemClickedId();
	    	
	    	Intent detailIntent = new Intent(this, CelebrityDetailActivity.class);
	        detailIntent.putExtra(CelebrityFragment.CELEBRITY_ID, thecelebidclicked);
	        startActivity(detailIntent);
	    }
	 
	 @Subscribe
	  public void onTrailerNeeded(TrailerNeeded goc){
	    	String thetraileridclicked=goc.getTrailerId();
	    	Intent theintent=new Intent(this,PlayVideoActivity.class);
	    	theintent.putExtra("videoid", thetraileridclicked);
	    	Log.v("videoid",thetraileridclicked+" intent");
	    	startActivity(theintent);
	    }
	  @Override
	    public void onResume() {
	        super.onResume();
	        EventBus.getInstance().register(this);
	      }
	    
	    @Override
	    public void onPause() {
	        super.onPause();
	        EventBus.getInstance().unregister(this);
	      }
	    
	    @Subscribe
		  public void onReviewsNeeded(ReviewsNeeded goc){
		    	String imdbid=goc.getimdbId();
		    	Intent intent = new Intent(this, ReviewsActivity.class);
		    	intent.putExtra("imdb",imdbid);
			    startActivity(intent);
		    }
	   
	

}
