package com.itamecodes.moviepot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;

public class CelebrityImageGalleryAdapter extends ArrayAdapter<String>{
	LayoutInflater inflater;
	private int resId;
	Context c;
	int reqWidth,reqHeight;
	
	AQuery listaq;
	public CelebrityImageGalleryAdapter(Context context, int gridElementResourceId,int width) {
		super(context, gridElementResourceId);
		c=context;
		inflater=LayoutInflater.from(context);
		this.resId=gridElementResourceId;
		
		this.reqWidth=this.reqHeight=width;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		View v=convertView;
		if(v==null){
			v=inflater.inflate(resId,null);
		}
		ImageView iv=(ImageView)v.findViewById(R.id.thegalleryimage);
		String poster=getItem(position);
		String theurl="http://cf2.imgobject.com/t/p/w154"+poster;
		AQuery aq = new AQuery(v);
		aq.id(iv).progress(R.id.progressgalleryimageelement).image(theurl,false,true,reqWidth,AQuery.INVISIBLE,null, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);
	
		return v;
	}
	
}
