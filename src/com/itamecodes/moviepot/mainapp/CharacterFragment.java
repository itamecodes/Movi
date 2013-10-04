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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.CharacterListAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.CharClickedObject;
import com.itamecodes.moviepot.loaders.CelebrityCharacterLoader;
import com.itamecodes.moviepot.utils.CelebrityItemClicked;

public class CharacterFragment extends BaseFragment implements
		LoaderCallbacks<ArrayList<HashMap<String, String>>>,
		OnItemClickListener {
	CharacterListAdapter cAd;
	static final String TAG = "CharacterFragment";
	AQuery aq;
	ProgressBar progbar;
	public CharacterFragment() {

	}

	public static CharacterFragment newInstance(String id) {
		CharacterFragment mFrag = new CharacterFragment();
		Log.v(TAG,id);
		Bundle args = new Bundle();
		args.putString("id", id);
		mFrag.setArguments(args);
		return mFrag;
	}

	public String getargsId() {
		Bundle bundle = getArguments();
		String id = bundle.getString("id");
		Log.v(TAG,id);
		return id;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {
		
		View v = inflater.inflate(R.layout.celebritycredits, container, false);
		GridView lv = (GridView)v.findViewById(R.id.thecharacterlist);
		cAd = new CharacterListAdapter(getActivity(), R.layout.characterlistelement,
				getResources().getDimensionPixelSize(R.dimen.casticonwidth));
		lv.setAdapter(cAd);
		lv.setOnItemClickListener(this);
		aq=new AQuery(getActivity());
		progbar=(ProgressBar)v.findViewById(R.id.progresscelebcredit);
		progbar.setVisibility(ProgressBar.VISIBLE);
		
		
		getLoaderManager().initLoader(1, null, this);
		
		

		return v;
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		EventBus.getInstance().register(this);
	}
	
	@Override
	public void onPause() {
		
		EventBus.getInstance().unregister(this);
	    GridView gridView = (GridView) getActivity().findViewById(R.id.thecharacterlist);
	    int count = gridView.getCount();
	    for (int i = 0; i < count; i++) {
	    	RelativeLayout v = (RelativeLayout) gridView.getChildAt(i);
	        if (v != null) {
	        	ImageView vi=(ImageView)v.findViewById(R.id.thecharactericon);
	        	TextView tv=(TextView) v.findViewById(R.id.thecharactername);
	        	ProgressBar pi=(ProgressBar)v.findViewById(R.id.progressBarcharacter);
	        	
	        			
	        	tv=null;
	            if (vi.getDrawable() != null) vi.getDrawable().setCallback(null);
	            vi=null;
	            pi=null;
	        	
	        }
	    }
	    super.onPause();
	}
	@Override
	public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(
			int loaderid, Bundle arg1) {
		
		progbar.setVisibility(View.VISIBLE);
		switch (loaderid) {
		
		case 1: {
			// get movie cast
			return new CelebrityCharacterLoader(getActivity().getApplicationContext(),
					getargsId());

		}
		

		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<HashMap<String, String>>> theLoader,
			ArrayList<HashMap<String, String>> data) {
		int theloaderid=theLoader.getId();
		switch(theloaderid){
		
		case 1:{
			cAd.clear();
			cAd.addAll(data);
			break;
		}
	
		}
		progbar.setVisibility(View.GONE);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> arg0) {

	}
	@Override
	public void onStop(){
		super.onStop();
	
	}
	

	@Override
	public void onItemClick(AdapterView<?> thegrid, View v, int position, long id) {
		HashMap<String,String> thelochm=cAd.getItem(position);
		String movieid=thelochm.get("movid");
		CharClickedObject goc=new CharClickedObject(movieid);
		EventBus.getInstance().post(goc);
		

	}
}
