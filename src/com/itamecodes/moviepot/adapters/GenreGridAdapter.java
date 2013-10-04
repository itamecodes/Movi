package com.itamecodes.moviepot.adapters;

import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GenreGridAdapter extends ArrayAdapter<HashMap<String,String>>{
	LayoutInflater inflater;
	private int resId;
	static final String TAG="GenreGridAdapter";
	public GenreGridAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		inflater=LayoutInflater.from(context);
		this.resId=textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		View v=convertView;
		ViewHolder vh;
		if(v==null){
			v=inflater.inflate(resId,null);
			vh=new ViewHolder();
			vh.tv=(TextView) v;
			v.setTag(vh);
		}else{
			vh=(ViewHolder) v.getTag();
		}
		HashMap<String,String> hm=getItem(position);
		Log.v(TAG,hm.get("name"));
		vh.tv.setText(hm.get("name"));
		return v;
	}
	private class ViewHolder{
		TextView tv;
	}
}
