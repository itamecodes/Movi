package com.itamecodes.moviepot.adapters;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.itamecodes.moviepot.R;

public class CharacterListAdapter extends ArrayAdapter<HashMap<String,String>>{
	LayoutInflater inflater;
	private int resId;
	int reqWidth,reqHeight;
	static final String TAG="CharacterListAdapter";
	

	Context c;
	public CharacterListAdapter(Context context, int listElementResourceId,int width) {
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
		TextView tv1=(TextView) v.findViewById(R.id.thecharactername);
		ImageView iv=(ImageView)v.findViewById(R.id.thecharactericon);
		
		HashMap<String,String> hm=getItem(position);
		tv1.setText(hm.get("character"));
		String url=hm.get("posterpath");
		
		String theurl="http://cf2.imgobject.com/t/p/w185"+url;
		AQuery aq = new AQuery(v);
		aq.id(iv).image(theurl,false,true,reqWidth,AQuery.INVISIBLE,null,0,AQuery.RATIO_PRESERVE);
		
		return v;
	}
	
}
