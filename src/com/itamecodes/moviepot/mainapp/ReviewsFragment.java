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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.ReviewAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.loaders.ReviewsLoader;

public class ReviewsFragment extends BaseFragment implements
		LoaderCallbacks<ArrayList<HashMap<String, String>>> {
	static final String TAG = "ReviewsFragment";
	ReviewAdapter cAd;
	AQuery aq;
	ProgressBar progbar;
	

	public ReviewsFragment() {

	}

	public static ReviewsFragment newInstance(String id) {
		ReviewsFragment mFrag = new ReviewsFragment();
		Bundle args = new Bundle();
		args.putString("id", id);
		mFrag.setArguments(args);
		return mFrag;
	}
	
	@Override
	public void onCreate(Bundle icic){
		 super.onCreate(icic);
		 getLoaderManager().initLoader(0, null, this);
		 
	}

	public String getargsId() {
		Bundle bundle = getArguments();
		String id = bundle.getString("id");
		return id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {

		View v = inflater.inflate(R.layout.reviewdetail, container, false);
		cAd = new ReviewAdapter(getActivity(), R.layout.thereviewlistelement);
		ListView lv=(ListView)v.findViewById(R.id.thereviewlist);
		lv.setAdapter(cAd);
		progbar = (ProgressBar) v.findViewById(R.id.progressBarreview);
		progbar.setVisibility(View.VISIBLE);
		
		return v;

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

	@Override
	public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(
			int loaderid, Bundle arg1) {

		switch (loaderid) {
		case 0: {
			// get movie description
			return new ReviewsLoader(getActivity()
					.getApplicationContext(), getargsId());
		}

		}
		return null;
	}

	@Override
	public void onLoadFinished(
			Loader<ArrayList<HashMap<String, String>>> theLoader,
			ArrayList<HashMap<String, String>> data) {
		int theloaderid = theLoader.getId();
		switch (theloaderid) {
		case 0: {
			Log.v(TAG,"am here");
			try{
			HashMap<String,String> thereviewsynop=data.get(0);
			String criticconsensus=thereviewsynop.get("consensus");
			if(ITCApplication.isNotNullNotEmptyNotWhiteSpaceOnly(criticconsensus)){
				TextView reviewsynopview=(TextView)getActivity().findViewById(R.id.thereviewsynop);
				reviewsynopview.setVisibility(TextView.VISIBLE);
				reviewsynopview.setText(criticconsensus);
			}
			ArrayList<HashMap<String, String>> thedata=new ArrayList<HashMap<String, String>>();
			thedata.addAll(data);
			thedata.remove(0);
			if(thedata.isEmpty()){
				TextView reviewsynopview=(TextView)getActivity().findViewById(R.id.thereviewsynop);
				reviewsynopview.setVisibility(TextView.VISIBLE);
				reviewsynopview.setText("Sorry no reviews were found for this movie");
			}
			cAd.clear();
			cAd.addAll(thedata);
			}catch(Exception e){
				
			}
			
		}

		}
		progbar.setVisibility(View.GONE);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> arg0) {

	}

	@Override
	public void onStop() {
		super.onStop();

	}

}
