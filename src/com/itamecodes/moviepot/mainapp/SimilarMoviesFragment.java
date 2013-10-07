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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.adapters.ImageTextAdapter;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.eventobjects.GridClickedObject;
import com.itamecodes.moviepot.loaders.SimilarMoviesLoader;

public class SimilarMoviesFragment extends BaseFragment implements
		LoaderCallbacks<ArrayList<HashMap<String, String>>>, OnScrollListener,
		OnItemClickListener {
	ImageTextAdapter upAd;
	int total_pages;
	int page = 1;
	static final String TAG = "SimilarMoviesFragment";
	ProgressBar progbar;
    TextView nosimilarmovies;
	public SimilarMoviesFragment() {

	}

	public static SimilarMoviesFragment newInstance(String movieid) {
		Log.v(TAG, movieid);
		SimilarMoviesFragment umf = new SimilarMoviesFragment();
		Bundle bundle = new Bundle();
		bundle.putString("movieid", movieid);
		umf.setArguments(bundle);
		return umf;
	}

	public String getMovieid() {
		String movieid = getArguments().getString("movieid");
		return movieid;
	}

	@Override
	public void onCreate(Bundle icic) {
		super.onCreate(icic);
		upAd = new ImageTextAdapter(getActivity(), R.layout.thegridelement,getResources().getDimensionPixelSize(R.dimen.iconwidth));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {
		View v = inflater.inflate(R.layout.thegrid, container, false);
		final GridView gv = (GridView) v.findViewById(R.id.thegridview);
		nosimilarmovies=(TextView)v.findViewById(R.id.textforsearch);
		gv.setAdapter(upAd);
		Log.v(TAG, "2");
		gv.setOnScrollListener(this);
		Log.v(TAG, "3");
		gv.setOnItemClickListener(this);
		progbar = (ProgressBar) v.findViewById(R.id.progress);
		progbar.setVisibility(ProgressBar.VISIBLE);
		Bundle args = new Bundle();
		args.putString("page", String.valueOf(page));
		Log.v(TAG, "4");
	
		
		getLoaderManager().initLoader(1, args, this);
		
		Log.v(TAG, "1");
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
			int loaderId, Bundle thebundle) {

		if (loaderId == 1) {
			String page = thebundle.getString("page");
			return new SimilarMoviesLoader(getActivity()
					.getApplicationContext(), page, getMovieid());
		}
		return null;
	}

	@Override
	public void onLoadFinished(
			Loader<ArrayList<HashMap<String, String>>> theLoader,
			ArrayList<HashMap<String, String>> data) {
		try{
			String totalpages = data.get(0).get("totalpages");
			total_pages = Integer.parseInt(totalpages);
			if(data.isEmpty()){
				nosimilarmovies.setVisibility(View.VISIBLE);
				nosimilarmovies.setText("No similar movies found");
			}
			upAd.clear();
			upAd.addAll(data);
			progbar.setVisibility(View.GONE);
			
		}catch(Exception e){
			nosimilarmovies.setVisibility(View.VISIBLE);
			nosimilarmovies.setText("No similar movies found");
		}
		
	}

	@Override
	public void onLoaderReset(
			Loader<ArrayList<HashMap<String, String>>> theLoader) {

	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		/*
		 * Log.v("thethread","am scrolling"+firstVisibleItem+"--"+visibleItemCount
		 * +"--"+totalItemCount); if(totalItemCount!=0){
		 * Log.v(TAG,totalItemCount+""); int
		 * shownGrid=firstVisibleItem+visibleItemCount;
		 * if(shownGrid>(totalItemCount-visibleItemCount)){
		 * if(page<total_pages){
		 * 
		 * Bundle args=new Bundle(); page=page+1;
		 * Log.v(TAG,"itemsshown="+shownGrid+"loadingpage="+page);
		 * args.putString("page", String.valueOf(page));
		 * getLoaderManager().restartLoader(1, args, this); } } }
		 */
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onItemClick(AdapterView<?> thegrid, View v, int position,
			long id) {
		Log.v("viveksimi","i clicked");
		HashMap<String, String> thelochm = upAd.getItem(position);
		String movieid = thelochm.get("id");
		GridClickedObject goc = new GridClickedObject(movieid);
		EventBus.getInstance().post(goc);
		Log.v("viveksimi", thelochm.get("title"));
	}

}
