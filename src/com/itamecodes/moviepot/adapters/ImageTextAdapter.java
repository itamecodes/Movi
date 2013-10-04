package com.itamecodes.moviepot.adapters;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;

public class ImageTextAdapter extends ArrayAdapter<HashMap<String,String>>{
	LayoutInflater inflater;
	private int resId;
	Context c;
	int reqWidth,reqHeight;
	
	public ImageTextAdapter(Context context, int gridElementResourceId,int width) {
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
		TextView tv=(TextView) v.findViewById(R.id.thetext);
		ImageView iv=(ImageView)v.findViewById(R.id.theimage);
		
		HashMap<String,String> hm=getItem(position);
		tv.setText((String)hm.get("title"));
		String url=hm.get("poster");
		String theurl="http://cf2.imgobject.com/t/p/w154"+url;
		
		AQuery aq = new AQuery(v);
		aq.id(iv).image(theurl,false,true,reqWidth,R.drawable.nomovie,null, AQuery.FADE_IN, 1.48f);
	
		return v;
	}
	
}
