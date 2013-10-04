package com.itamecodes.moviepot.loaders;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.itamecodes.moviepot.config.Config;
import com.itamecodes.moviepot.jsonobjects.CelebInfoObject;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class CelebrityDetaiLoader extends WrappedAsyncTaskLoader<ArrayList<HashMap<String,String>>> {
	/*
	 * This class loads the youtube image thumbnail and the description of the movie
	 */
	private String _celebid;
	static final String TAG="CelebrityDetaiLoader";
	public CelebrityDetaiLoader(Context c,String movieid) {
		super(c);
		this._celebid=movieid;
		Config config;
		
	}

	@Override
	public ArrayList<HashMap<String, String>> loadInBackground() {
		String url = "http://api.themoviedb.org/3/person/"+_celebid+"?api_key="
				+ Config.MDB.getKey();
		Log.v(TAG,url);
		HttpURLConnection urlConnection=null;
		ArrayList<HashMap<String,String>> al=new ArrayList<HashMap<String,String>>();
		try {
			URL theurl=new URL(url);
			urlConnection=(HttpURLConnection) theurl.openConnection();
			int maxStale = 60 * 60 * 24; // tolerate 4-weeks stale
			urlConnection.addRequestProperty("Cache-Control", "max-stale=" + maxStale);
			urlConnection.setRequestProperty("Accept", "application/json");
			InputStream is = new BufferedInputStream(urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String datafromServer=sb.toString();
			Log.v(TAG,datafromServer);
			Gson gson=new Gson();
			CelebInfoObject celebinfoobject=gson.fromJson(datafromServer, CelebInfoObject.class);
			String biography=celebinfoobject.biography;
			String name=celebinfoobject.name;
			String profile_path=celebinfoobject.profile_path;
			
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("biography", biography);
			hm.put("name", name);
			hm.put("profile_path", profile_path);
			
		
			al.add(hm);
			hm=null;
			return al;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			urlConnection.disconnect();
		}
		return al;
	}

}
