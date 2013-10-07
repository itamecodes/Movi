package com.itamecodes.moviepot.adapters;

import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itamecodes.moviepot.R;
import com.itamecodes.moviepot.mainapp.ITCApplication;

public class ReviewAdapter extends ArrayAdapter<HashMap<String,String>>{
	LayoutInflater inflater;
	private int resId;
	static final String TAG="CastListAdapter";
	
	Context c;
	public ReviewAdapter(Context context, int listElementResourceId) {
		super(context, listElementResourceId);
		c=context;
		inflater=LayoutInflater.from(context);
		this.resId=listElementResourceId;
		
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		View v=convertView;
		
		if(v==null){
			v=inflater.inflate(resId,null);
			
		}
		
		HashMap<String,String> hm=getItem(position);
		String thequote=hm.get("quote");
		String thefreshness=hm.get("freshness");
		String critic=hm.get("critic");
		String publication=hm.get("publication");
		if(ITCApplication.isNotNullNotEmptyNotWhiteSpaceOnly(thequote)){
			((TextView)v.findViewById(R.id.thereviewtext)).setText(thequote);
		}
		if(!TextUtils.isEmpty(thefreshness)){
			if(thefreshness.equalsIgnoreCase("fresh")){
				((ImageView)v.findViewById(R.id.rotfresh)).setBackgroundResource(R.drawable.fresh);
			}
			if(thefreshness.equalsIgnoreCase("rotten")){
				((ImageView)v.findViewById(R.id.rotfresh)).setBackgroundResource(R.drawable.rotten);
			}
			
		}
		if(ITCApplication.isNotNullNotEmptyNotWhiteSpaceOnly(critic)){
			((TextView)v.findViewById(R.id.thereviewauthor)).setText(critic);
		}
		
		if(ITCApplication.isNotNullNotEmptyNotWhiteSpaceOnly(publication)){
			((TextView)v.findViewById(R.id.thereviewpublication)).setText(publication);
		}
			
		
		return v;
	}
	
}
