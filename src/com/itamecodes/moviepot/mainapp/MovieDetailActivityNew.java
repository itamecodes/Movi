package com.itamecodes.moviepot.mainapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.MovementMethod;
import android.util.Log;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.GridClickedObject;
import com.itamecodes.moviepot.eventobjects.ReviewsNeeded;
import com.itamecodes.moviepot.utils.CelebrityItemClicked;
import com.itamecodes.moviepot.utils.TrailerNeeded;
import com.keyes.youtube.OpenYouTubePlayerActivity;
import com.squareup.otto.Subscribe;

public class MovieDetailActivityNew extends BaseActivity{
	ActionBar actionBar;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.v("vivekdeta",getIntent().getStringExtra(MovieDetailFragment.MOVIE_ID)+"---");
		
		actionBar=getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		Tab tab = actionBar.newTab().setText(R.string.movie_detail_activity)
		                       .setTabListener(new TabListener<MovieDetailFragment>(
		                               this, "moviedetail", MovieDetailFragment.class,getIntent().getStringExtra(MovieDetailFragment.MOVIE_ID)));
		tab.setTag("moviedetail");
		    actionBar.addTab(tab);
		    
		    tab = actionBar.newTab()
		                   .setText(R.string.cast)
		                   .setTabListener(new TabListener<MovieCastFragment>(
		                           this, "moviecast", MovieCastFragment.class,getIntent().getStringExtra(MovieDetailFragment.MOVIE_ID)));
		    tab.setTag("moviecast");
		    actionBar.addTab(tab);
		    
		    tab = actionBar.newTab()
	                   .setText(R.string.viewsimil)
	                   .setTabListener(new TabListener<SimilarMoviesFragment>(
	                           this, "moviecast", SimilarMoviesFragment.class,getIntent().getStringExtra(MovieDetailFragment.MOVIE_ID)));
	    tab.setTag("similar");
	    actionBar.addTab(tab);
		    
		    Log.v("vivekdeta","7");
		    if (savedInstanceState != null) {
	            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	        }
	}
	
	
	
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment movieDetailFragment,movieCastFragment,mFragment,movieSimilarFragment;
	    private final FragmentActivity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;
	    private android.support.v4.app.FragmentTransaction fft;
	    private String movieid;
	    


	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz,String movieidu) {
	        mActivity = (FragmentActivity) activity;
	        mTag = tag;
	        mClass = clz;
	        movieid=movieidu;
	        
	        mFragment = mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {
            	android.support.v4.app.FragmentTransaction fft = mActivity.getSupportFragmentManager().beginTransaction();
                fft.detach(mFragment);
                fft.commit();
            }
	        
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	    	android.support.v4.app.FragmentTransaction fft = mActivity.getSupportFragmentManager().beginTransaction();
	    	if(tab.getTag().equals("moviedetail")){
	    		
	    		 if (movieDetailFragment == null) {
	 	            // If not, instantiate and add it to the activity
	    			 movieDetailFragment = MovieDetailFragment
							.newInstance(movieid);
	 	            fft.add(android.R.id.content, movieDetailFragment, mTag);
	 	            fft.commit();
	 	        } else {
	 	            // If it exists, simply attach it in order to show it
	 	            fft.attach(movieDetailFragment);
	 	            fft.commit();
	 	        }
	    		
	    		Log.v("vivekdeta","moviedetail");
	    		
	    		
	    	}
	    	if(tab.getTag()=="moviecast"){
	    		 if (movieCastFragment == null) {
		 	            // If not, instantiate and add it to the activity
	    			 movieCastFragment = MovieCastFragment
								.newInstance(movieid);
		 	            fft.add(android.R.id.content, movieCastFragment, mTag);
		 	            fft.commit();
		 	        } else {
		 	            // If it exists, simply attach it in order to show it
		 	            fft.attach(movieCastFragment);
		 	           fft.commit();
		 	        }
		    		
		    		Log.v("vivekdeta","moviedetail");
	    		
	    	}
	    	
	    	if(tab.getTag()=="similar"){
	    		 if (movieSimilarFragment == null) {
		 	            // If not, instantiate and add it to the activity
	    			 movieSimilarFragment = SimilarMoviesFragment
								.newInstance(movieid);
		 	            fft.add(android.R.id.content, movieSimilarFragment, mTag);
		 	            fft.commit();
		 	        } else {
		 	            // If it exists, simply attach it in order to show it
		 	            fft.attach(movieSimilarFragment);
		 	           fft.commit();
		 	        }
		    		
		    		Log.v("vivekdeta","moviedetail");
	    		
	    	}
	    	
	      
	    }

	    
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	    	android.support.v4.app.FragmentTransaction fft = mActivity.getSupportFragmentManager().beginTransaction();
	        if (movieCastFragment != null) {
	            // Detach the fragment, because another one is being attached
	            fft.detach(movieCastFragment);
	           fft.commit();
	        }
	        if(movieDetailFragment!=null){
	        	fft.detach(movieDetailFragment);
	        	fft.commit();
	        }
	        if(movieSimilarFragment!=null){
	        	fft.detach(movieSimilarFragment);
	        	fft.commit();
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }



	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

	 @Subscribe
	    public void onGridItemClicked(GridClickedObject goc){
		 Log.v("viveksimilar",""+goc.getId());
	     String themovieidclicked=goc.getId();
	     Intent detailIntent = new Intent(this, MovieDetailActivityNew.class);
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
