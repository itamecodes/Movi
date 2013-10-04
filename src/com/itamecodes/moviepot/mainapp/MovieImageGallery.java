package com.itamecodes.moviepot.mainapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.MovieImageGalleryAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.loaders.MovieImageGalleryLoader;

public class MovieImageGallery extends BaseFragment implements LoaderCallbacks<ArrayList<String>>{
	AQuery aq;
	MovieImageGalleryAdapter cAd;
	ProgressBar progbar;
	static final String TAG="MovieImageGallery";
	public MovieImageGallery() {

	}

	public static MovieImageGallery newInstance(String id) {
		MovieImageGallery mFrag = new MovieImageGallery();
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
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {
		
		
		
		View v=inflater.inflate(R.layout.galleryimagegrid, container, false);
		
		
		GridView lv = (GridView) v.findViewById(R.id.thegridviewgallery);
	
		cAd = new MovieImageGalleryAdapter(getActivity(), R.layout.thegalleryimageelement,
				getResources().getDimensionPixelSize(R.dimen.galleryimagewidth));
		
		lv.setAdapter(cAd);
		
		progbar=(ProgressBar)v.findViewById(R.id.progressgalleryimage);
		progbar.setVisibility(ProgressBar.VISIBLE);
		
		getLoaderManager().initLoader(0, null, this);
		

		return v;
	}
	
	


	@Override
	public Loader<ArrayList<String>> onCreateLoader(int arg0,
			Bundle arg1) {
		return new MovieImageGalleryLoader(getActivity().getApplicationContext(),
				getargsId());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<String>> theLoader,
			ArrayList<String> data) {
		cAd.clear();
		cAd.addAll(data);
		progbar.setVisibility(ProgressBar.GONE);
		
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<String>> arg0) {
		
	}
	
	
	@Override
	public void onPause() {
		
		EventBus.getInstance().unregister(this);
	    GridView gridView = (GridView) getActivity().findViewById(R.id.thegridviewgallery);
	    int count = gridView.getCount();
	    for (int i = 0; i < count; i++) {
	    	RelativeLayout v = (RelativeLayout) gridView.getChildAt(i);
	        if (v != null) {
	        	ImageView vi=(ImageView)v.findViewById(R.id.thegalleryimage);
	        	ProgressBar pi=(ProgressBar)v.findViewById(R.id.progressgalleryimageelement);
	        	
	        			
	            if (vi.getDrawable() != null) vi.getDrawable().setCallback(null);
	            vi=null;
	            pi=null;
	        	
	        }
	    }
	    super.onPause();
	}


}
