package com.itamecodes.moviepot.mainapp;

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
import android.view.MenuItem;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.CharClickedObject;
import com.squareup.otto.Subscribe;

public class CelebrityDetailActivity extends BaseActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	static final String TAG="CelebrityDetailloader";
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
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0: {
				CelebrityFragment fragment = CelebrityFragment
						.newInstance(getIntent().getStringExtra(
								CelebrityFragment.CELEBRITY_ID));
				return fragment;
			}
			case 1: {
				
				CharacterFragment fragment = CharacterFragment
						.newInstance(getIntent().getStringExtra(
								CelebrityFragment.CELEBRITY_ID));
				return fragment;
			}
			case 2: {
				
				CelebrityImageGallery fragment = CelebrityImageGallery
						.newInstance(getIntent().getStringExtra(
								CelebrityFragment.CELEBRITY_ID));
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
				return getString(R.string.celebrity).toUpperCase();
			case 1:
				return getString(R.string.credits).toUpperCase();
			case 2:
				return getString(R.string.gallery).toUpperCase();
			
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

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
	    public void onCharItemClicked(CharClickedObject goc){
	    	String themovieidclicked=goc.getId();
	    	
	    	Intent detailIntent = new Intent(this, MovieDetailActivityNew.class);
	        detailIntent.putExtra(MovieDetailFragment.MOVIE_ID, themovieidclicked);
	        startActivity(detailIntent);
	    }
	    

}
