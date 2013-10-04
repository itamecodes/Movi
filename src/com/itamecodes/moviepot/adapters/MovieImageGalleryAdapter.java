package com.itamecodes.moviepot.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;
import com.itamecodes.moviepot.R;

public class MovieImageGalleryAdapter extends ArrayAdapter<String> {
	LayoutInflater inflater;
	private int resId;
	Context c;
	int reqWidth, reqHeight;
	static final String TAG = "MovieImageGalleryAdapter";
	AQuery listaq;

	public MovieImageGalleryAdapter(Context context, int gridElementResourceId,
			int width) {
		super(context, gridElementResourceId);
		c = context;
		inflater = LayoutInflater.from(context);
		listaq = new AQuery(c);
		this.resId = gridElementResourceId;

		this.reqWidth = this.reqHeight = width;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		View v = convertView;
		WowViewHolder vh;
		if (v == null) {
			v = inflater.inflate(resId, null);

			vh = new WowViewHolder();
			vh.iv = (ImageView) v.findViewById(R.id.thegalleryimage);
			v.setTag(vh);

			// vh.tv=(TextView) v.findViewById(R.id.thetext);

		} else {
			vh = (WowViewHolder) v.getTag();
		}
		String poster = getItem(position);
		// vh.tv.setText((String)hm.get("title"));
		// String url=hm.get("poster");
		String theurl = "http://cf2.imgobject.com/t/p/w154" + poster;

		AQuery aq = listaq.recycle(v);
		aq.id(vh.iv)
		.image(theurl, false, true, reqWidth,
						AQuery.INVISIBLE, null, AQuery.FADE_IN,
						AQuery.RATIO_PRESERVE);

		return v;
	}

	private class WowViewHolder {
		// TextView tv;
		ImageView iv;
	}
}
