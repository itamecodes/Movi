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

public class CastListAdapter extends ArrayAdapter<HashMap<String,String>>{
	LayoutInflater inflater;
	private int resId;
	int reqWidth,reqHeight;
	static final String TAG="CastListAdapter";
	
	Context c;
	public CastListAdapter(Context context, int listElementResourceId,int width) {
		super(context, listElementResourceId);
		c=context;
		inflater=LayoutInflater.from(context);
		this.resId=listElementResourceId;
		this.reqWidth=this.reqHeight=width;
		
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		View v=convertView;
		
		if(v==null){
			v=inflater.inflate(resId,null);
			
		}
		TextView tv=(TextView) v.findViewById(R.id.thecastname);
		ImageView iv=(ImageView)v.findViewById(R.id.thecasticon);
		HashMap<String,String> hm=getItem(position);
	
		tv.setText(hm.get("name"));
		String url=hm.get("profilepath");
		 AQuery aq = new AQuery(v);
		
			
		String theurl="http://cf2.imgobject.com/t/p/w185"+url;
		
		aq.id(iv).image(theurl,false,true,reqWidth,R.drawable.personstub,null,0,1);
		
		
		return v;
	}
	
}
