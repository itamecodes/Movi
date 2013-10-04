package com.itamecodes.moviepot.mainapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.CastListAdapter;
import com.itamecodes.moviepot.adapters.GenreGridAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.ReviewsNeeded;
import com.itamecodes.moviepot.loaders.MovieDetaiLoader;
import com.itamecodes.moviepot.loaders.MovieTrailerLoader;
import com.itamecodes.moviepot.utils.CelebrityItemClicked;
import com.itamecodes.moviepot.utils.SimilarMovieNeeded;
import com.itamecodes.moviepot.utils.TrailerNeeded;

public class MovieDetailFragment extends BaseFragment implements
		LoaderCallbacks<ArrayList<HashMap<String, String>>>,
		OnItemClickListener {
	CastListAdapter cAd;
	ArrayList<String> themenus=new ArrayList<String>();
	AQuery aq;
	ProgressBar progbar;
	GenreGridAdapter thegenregridAdapter;
	static final String TAG = "MovieDetailFragment";
	static  String MOVIE_ID="movieid";
	String trailer;
    
	public MovieDetailFragment() {

	}

	public static MovieDetailFragment newInstance(String id) {
		MovieDetailFragment mFrag = new MovieDetailFragment();
		Bundle args = new Bundle();
		args.putString("id", id);
		mFrag.setArguments(args);
		
		return mFrag;
	}

	public String getargsId() {
		Bundle bundle = getArguments();
		String id = bundle.getString("id");
		return id;
	}
	
	@Override
	public void onCreate(Bundle ici){
		super.onCreate(ici);
		themenus.add("View Similar");
		
		aq=new AQuery(getActivity());
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
		 super.onCreateOptionsMenu(menu, inflater);
		 //inflater.inflate(R.menu.similar, menu);
		 
		 /*
		for(String themenuoption:themenus){
			menu.add(themenuoption).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);	
		}*/
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		
		}
		/*
		String theclickedmenu=item.getTitle().toString();
		if(theclickedmenu=="View Similar"){
			String themovieid=getargsId();
			SimilarMovieNeeded simiNeeded=new SimilarMovieNeeded(themovieid);
			EventBus.getInstance().post(simiNeeded);
		}*/
		
	
		
		return false;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {
		
		
		
		View v=inflater.inflate(R.layout.themoviedetail, container, false);
	
		progbar=(ProgressBar)v.findViewById(R.id.progressmoviedetail);
		progbar.setVisibility(ProgressBar.VISIBLE);
	
		
		getLoaderManager().initLoader(0, null, this);
		getLoaderManager().initLoader(2, null, this);
		
		return v;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		EventBus.getInstance().register(this);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		EventBus.getInstance().unregister(this);
	}
	
	public static boolean isNotNullNotEmptyNotWhiteSpaceOnly(final String string)
			{
			   return string != null && !string.isEmpty() && !string.trim().isEmpty();
			}

	@Override
	public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(
			int loaderid, Bundle arg1) {
		
		switch (loaderid) {
		case 0: {
			// get movie description
			return new MovieDetaiLoader(getActivity().getApplicationContext(),
					getargsId());
		}
	
		case 2: {
			// get movie trailer
			return new MovieTrailerLoader(
					getActivity().getApplicationContext(), getargsId());
		}

		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<HashMap<String, String>>> theLoader,
			ArrayList<HashMap<String, String>> data) {
		int theloaderid=theLoader.getId();
		Button trailerbutton=((Button)(getActivity().findViewById(R.id.themovietrailerbutton)));
		Button reviewbutton=((Button)(getActivity().findViewById(R.id.themoviereviewbutton)));
		switch(theloaderid){
		case 0:{
			HashMap<String,String> lochm=data.get(0);
			//String dataincaption="<string name='sample_string'><![CDATA[some test line 1 <br />some test line 2]]></string>";
			String backdroppath=lochm.get("backdrop");
			String caption=lochm.get("caption");
			String overview=lochm.get("overview");
			String moviename=lochm.get("title");
			String genrestring=lochm.get("genres");
			String popularity=lochm.get("popularity");
			String vote_average=lochm.get("vote_average");
			String release_date=lochm.get("release_date");
			String imdb=lochm.get("imdb");
					
			if(isNotNullNotEmptyNotWhiteSpaceOnly(moviename)){
			TextView movienameview=((TextView)(getActivity().findViewById(R.id.themoviename)));
			
			movienameview.setVisibility(TextView.VISIBLE);
			movienameview.setText(Html.fromHtml(moviename));
			}
			
			if(isNotNullNotEmptyNotWhiteSpaceOnly(overview)){
			
			((TextView)(getActivity().findViewById(R.id.themoviedesc))).setText(overview);
			}
			if(isNotNullNotEmptyNotWhiteSpaceOnly(caption)){
			TextView moviecaptionview=((TextView)(getActivity().findViewById(R.id.themoviecaption)));
			moviecaptionview.setVisibility(TextView.VISIBLE);
			moviecaptionview.setText("\""+caption+"\"");
			}
			//set popularity
			/*
			if(isNotNullNotEmptyNotWhiteSpaceOnly(popularity)){
				TextView moviepopview=((TextView)(getActivity().findViewById(R.id.themoviepopularity)));
				moviepopview.setText("Popularity - " +popularity+"%");
			}
			*/
			//average votes
			if(isNotNullNotEmptyNotWhiteSpaceOnly(vote_average)){
				TextView movieaveragevotes=((TextView)(getActivity().findViewById(R.id.themovieaveragevotes)));
				movieaveragevotes.setText("Average Votes - " + vote_average+"/10");
				movieaveragevotes.setVisibility(TextView.VISIBLE);
			}
			
			
			
			//release date
			if(isNotNullNotEmptyNotWhiteSpaceOnly(release_date)){
				TextView moviereleasedate=((TextView)(getActivity().findViewById(R.id.themoviereleasedate)));
				String thereleasedate=formatDate(release_date);
				if(isNotNullNotEmptyNotWhiteSpaceOnly(thereleasedate)){
					moviereleasedate.setText("Release Date - "+ thereleasedate);
					moviereleasedate.setVisibility(TextView.VISIBLE);
				}
				
			}
			
			//genres
			if(isNotNullNotEmptyNotWhiteSpaceOnly(genrestring)){
				TextView moviegenrestr=((TextView)(getActivity().findViewById(R.id.themoviegenrestring)));
				moviegenrestr.setText("Genres - "+ genrestring);
				moviegenrestr.setVisibility(TextView.VISIBLE);
			}
			if(isNotNullNotEmptyNotWhiteSpaceOnly(imdb)){
				Log.v(TAG,"imdb= "+imdb);
				final String theimdb=imdb.substring(2);
				Log.v(TAG,"imdb= "+theimdb);
				reviewbutton.setVisibility(Button.VISIBLE);
				reviewbutton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ReviewsNeeded rn=new ReviewsNeeded(theimdb);
						EventBus.getInstance().post(rn);
					}
				});
			}
			
		
			String url="http://cf2.imgobject.com/t/p/w300"+backdroppath;
			aq.id(R.id.theyoutubeimage).image(url,true,true,getResources().getDimensionPixelSize(R.dimen.youtubeimage),R.drawable.noimagemoviebg,null,AQuery.FADE_IN,AQuery.RATIO_PRESERVE);
			if(isNotNullNotEmptyNotWhiteSpaceOnly(trailer)){
			trailerbutton.setVisibility(Button.VISIBLE);
			}
			break;
		}
		
		case 2:{
			try{
				
		
			HashMap<String,String> lochm=data.get(0);
			trailer=lochm.get("id");
			if(isNotNullNotEmptyNotWhiteSpaceOnly(trailer)){
				
				trailerbutton.setVisibility(Button.VISIBLE);
				trailerbutton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.v("videoid","clicked"+trailer);
						TrailerNeeded trailerNeeded=new TrailerNeeded(trailer);
						EventBus.getInstance().post(trailerNeeded);
						
					}
				});
			}
			
		}catch(Exception e){
			
		}
		
		}
		}
		
		progbar.setVisibility(ProgressBar.GONE);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> arg0) {

	}
	@Override
	public void onStop(){
		super.onStop();
		
	}
	
	public void onViewTrailer(View v) {
		
	      }

	@Override
	public void onItemClick(AdapterView<?> listView, View v, int position,
			long id) {
		int viewid=listView.getId();
		switch(viewid){
		case(R.id.thecastlist):{
			HashMap<String, String> lochm = cAd.getItem(position);
			String idOfActor = lochm.get("id");
			CelebrityItemClicked gc=new CelebrityItemClicked(idOfActor);
			EventBus.getInstance().post(gc);
			break;
		}
		
		}
		

	}
}
