package com.itamecodes.moviepot.mainapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;
import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.CastListAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.loaders.CastListLoader;
import com.itamecodes.moviepot.utils.CelebrityItemClicked;

public class MovieCastFragment extends BaseFragment implements LoaderCallbacks<ArrayList<HashMap<String, String>>>,OnItemClickListener {
	AQuery aq;
	CastListAdapter cAd;
	ProgressBar progbar;
	static final String TAG="MovieCastFragment";
	public MovieCastFragment() {

	}

	public static MovieCastFragment newInstance(String id) {
		MovieCastFragment mFrag = new MovieCastFragment();
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
		aq=new AQuery(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {
		
		
		
		View v=inflater.inflate(R.layout.thegridformoviecast, container, false);
		
		
		GridView lv = (GridView) v.findViewById(R.id.thecastlist);
		cAd = new CastListAdapter(getActivity(), R.layout.castlistelement,
				getResources().getDimensionPixelSize(R.dimen.casticonwidth));
		lv.setAdapter(cAd);
		lv.setOnItemClickListener(this);
		aq.id(lv).scrolled(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				AQUtility.debug("forward", "onScrollStateChanged");
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				AQUtility.debug("forward", "onScroll");
			}
		});
		progbar=(ProgressBar)v.findViewById(R.id.progresscast);
		progbar.setVisibility(ProgressBar.VISIBLE);
		
		
		getLoaderManager().initLoader(0, null, this);
		

		return v;
	}
	
	


	@Override
	public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(int arg0,
			Bundle arg1) {
		return new CastListLoader(getActivity().getApplicationContext(),
				getargsId());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<HashMap<String, String>>> theLoader,
			ArrayList<HashMap<String, String>> data) {
		cAd.clear();
		cAd.addAll(data);
		progbar.setVisibility(ProgressBar.GONE);
		
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> arg0) {
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		EventBus.getInstance().register(this);
	}
	
	@Override
	public void onPause() {
		
		EventBus.getInstance().unregister(this);
	    GridView gridView = (GridView) getActivity().findViewById(R.id.thecastlist);
	    int count = gridView.getCount();
	    for (int i = 0; i < count; i++) {
	    	RelativeLayout v = (RelativeLayout) gridView.getChildAt(i);
	        if (v != null) {
	        	ImageView vi=(ImageView)v.findViewById(R.id.thecasticon);
	        	TextView tv=(TextView) v.findViewById(R.id.thecastname);
	        	
	        			
	        	tv=null;
	            if (vi.getDrawable() != null) vi.getDrawable().setCallback(null);
	            vi=null;
	        	
	        }
	    }
	    super.onPause();
	}


	@Override
	public void onItemClick(AdapterView<?> thegrid, View v, int position, long id) {
		HashMap<String,String> thelochm=cAd.getItem(position);
		String movieid=thelochm.get("id");
		CelebrityItemClicked goc=new CelebrityItemClicked(movieid);
		EventBus.getInstance().post(goc);
	}

}
