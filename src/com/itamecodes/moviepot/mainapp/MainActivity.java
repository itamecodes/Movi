package com.itamecodes.moviepot.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.GridClickedObject;
import com.squareup.otto.Subscribe;

public class MainActivity extends BaseActivity{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private static final String TAG="MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	if(!isNetworkAvailable(this)){
    		showAlert();
    	}else{
        
        
        setContentView(R.layout.activity_main);
        if (getResources().getBoolean(R.bool.isLargeTablet)){
        	Log.v(TAG,"am large");
        }
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    	}
    }

    

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return false;
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
        super.onPause();
        EventBus.getInstance().unregister(this);
      }
    
    @Subscribe
    public void onGridItemClicked(GridClickedObject goc){
    	String themovieidclicked=goc.getId();
    
    	Intent detailIntent = new Intent(this, MovieDetailActivityNew.class);
        detailIntent.putExtra(MovieDetailFragment.MOVIE_ID, themovieidclicked);
        startActivity(detailIntent);
    }
    

    



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	switch(i){
        	case 0:{
        		  Fragment fragment = UpcomingMoviesFragment.newInstance();
                 
                  return fragment;
        	}
        	case 1:{
        		 Fragment fragment = PopularMovieFragment.newInstance();
                 
                 return fragment;
        	}
        	case 2:{
        		 Fragment fragment = TopRatedMoviesFragment.newInstance();
                 
                 return fragment;
        	}
        	case 3:{
        		 Fragment fragment = NowPlayingMoviesFragment.newInstance();
                 
                 return fragment;
        	}
        	}
        	return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.title_section1).toUpperCase();
                case 1: return getString(R.string.title_section2).toUpperCase();
                case 2: return getString(R.string.title_section3).toUpperCase();
                case 3: return getString(R.string.title_section4).toUpperCase();
            }
            return null;
        }
    }

   
}
