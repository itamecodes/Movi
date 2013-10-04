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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.eventbus.EventBus;
import com.itamecodes.moviepot.loaders.CelebrityDetaiLoader;

public class CelebrityFragment extends BaseFragment implements
		LoaderCallbacks<ArrayList<HashMap<String, String>>> {
	static final String TAG = "CelebrityFragment";
	AQuery aq;
	ProgressBar progbar;
	static String CELEBRITY_ID = "celebid";

	public CelebrityFragment() {

	}

	public static CelebrityFragment newInstance(String id) {
		CelebrityFragment mFrag = new CelebrityFragment();
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle icic) {

		View v = inflater.inflate(R.layout.celebritydetail, container, false);

		aq = new AQuery(getActivity());
		progbar = (ProgressBar) v.findViewById(R.id.progressBarcelebrity);
		progbar.setVisibility(View.VISIBLE);
		getLoaderManager().initLoader(0, null, this);
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
			return new CelebrityDetaiLoader(getActivity()
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
			HashMap<String, String> lochm = data.get(0);
			String backdroppath = lochm.get("profile_path");
			String caption = lochm.get("name");
			String biography = lochm.get("biography");

			((TextView) (getActivity().findViewById(R.id.thecelebdesc)))
					.setText(biography);
			TextView celebnameview=((TextView) (getActivity().findViewById(R.id.thecelebrityname)));
			celebnameview.setVisibility(TextView.VISIBLE);
			
			celebnameview.setText(caption);

			String url = "http://cf2.imgobject.com/t/p/w185" + backdroppath;
			Log.v("viveku",url);
			aq.id(R.id.thecelebrityimage).image(
					url,
					true,
					true,
					getResources()
							.getDimensionPixelSize(R.dimen.celebrityimage),
					R.drawable.personstub, null, 0,
					AQuery.RATIO_PRESERVE);

			break;
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
