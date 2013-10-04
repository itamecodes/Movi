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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.ImageTextAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.GridClickedObject;
import com.itamecodes.moviepot.loaders.SearchMoviesLoader;

public class SearchMovieFragment extends BaseFragment implements LoaderCallbacks<ArrayList<HashMap<String,String>>>,OnScrollListener,OnItemClickListener{
	ImageTextAdapter upAd;
	TextView searchfortext;
	int total_pages;
	int page=1;
	//ProgressDialog  dialog;
	static final String TAG="SearchMovieFragment";
	public SearchMovieFragment(){
		
	}


	public static SearchMovieFragment newInstance(String query){
	SearchMovieFragment umf=new SearchMovieFragment();
	Bundle bundle=new Bundle();
	bundle.putString("querystring",query);
	umf.setArguments(bundle);
	return umf;
	}
	
	public String getMovieQuery(){
		String querystring=getArguments().getString("querystring");
		return querystring;
	}
	
	@Override
	public void onCreate(Bundle icic){
		super.onCreate(icic);
		upAd=new ImageTextAdapter(getActivity(), R.layout.thegridelement,getResources().getDimensionPixelSize(R.dimen.iconwidth));
		
		
	}
	
	@Override
	public void onActivityCreated(Bundle icic){
		super.onCreate(icic);
		
		
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle icic){
		View v=inflater.inflate(R.layout.thegrid, container, false);
		final GridView gv = (GridView)v.findViewById(
				R.id.thegridview);
		searchfortext=(TextView)v.findViewById(R.id.textforsearch);
		gv.setAdapter(upAd);
		gv.setOnScrollListener(this);
		gv.setOnItemClickListener(this);
		Bundle args=new Bundle();
		args.putString("query", getMovieQuery());
		
		getLoaderManager().initLoader(1, args, this);
		
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

	@Override
	public void onDestroy(){
		super.onDestroy();
		
	}

	@Override
	public Loader<ArrayList<HashMap<String, String>>> onCreateLoader(int loaderId,Bundle thebundle) {
		
		if(loaderId==1){
			String page=thebundle.getString("query");
			return new SearchMoviesLoader(getActivity().getApplicationContext(),page);
		}
		
		return null;
	}


	@Override
	public void onLoadFinished(Loader<ArrayList<HashMap<String, String>>> theLoader,ArrayList<HashMap<String, String>> data) {
		//Log.v(TAG,"dismiss");
		try{
		String totalpages=data.get(0).get("totalpages");
		total_pages=Integer.parseInt(totalpages);
		if(data.isEmpty()){
			searchfortext.setVisibility(View.VISIBLE);
			searchfortext.setText("No Search Results found at this time");
		}
		upAd.clear();
		upAd.addAll(data);
		}catch(Exception e){
			searchfortext.setVisibility(View.VISIBLE);
			searchfortext.setText("No Search Results found at this time");
			//Toast.makeText(getActivity(),"Sorry I dont have any results for your search", Toast.LENGTH_LONG).show();
		}
		
		
	}


	@Override
	public void onLoaderReset(Loader<ArrayList<HashMap<String, String>>> theLoader) {
		
	}
	
	@Override
	public void onStop(){
		super.onStop();
		
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
	/*
		
		Log.v("thethread","am scrolling"+firstVisibleItem+"--"+visibleItemCount+"--"+totalItemCount);
		if(totalItemCount!=0){
			Log.v(TAG,totalItemCount+"");
			int shownGrid=firstVisibleItem+visibleItemCount;
			if(shownGrid>(totalItemCount-visibleItemCount)){
				if(page<total_pages){
					
					Bundle args=new Bundle();
					page=page+1;
					Log.v(TAG,"itemsshown="+shownGrid+"loadingpage="+page);
					args.putString("page", String.valueOf(page));
					getLoaderManager().restartLoader(1, args, this);
				}
			}
		}
		*/
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		
	}


	@Override
	public void onItemClick(AdapterView<?> thegrid, View v, int position, long id) {
		HashMap<String,String> thelochm=upAd.getItem(position);
		String movieid=thelochm.get("id");
		GridClickedObject goc=new GridClickedObject(movieid);
		EventBus.getInstance().post(goc);
		Log.v(TAG,thelochm.get("title"));
	}



	

}
