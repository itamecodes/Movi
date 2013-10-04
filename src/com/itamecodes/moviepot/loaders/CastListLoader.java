package com.itamecodes.moviepot.loaders;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.itamecodes.moviepot.config.Config;
import com.itamecodes.moviepot.jsonobjects.Actors;
import com.itamecodes.moviepot.jsonobjects.CastMovie;
import com.itamecodes.moviepot.jsonobjects.Crew;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class CastListLoader extends WrappedAsyncTaskLoader<ArrayList<HashMap<String,String>>> {
	private String _movieid;
	static final String TAG="CastListLoader";
	public CastListLoader(Context context,String movieid) {
		
		super(context);
		Config config;
		this._movieid=movieid;
	}

	@Override
	public ArrayList<HashMap<String,String>> loadInBackground() {
		String datafromServer = null;
		ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
		
		HttpURLConnection urlConnection=null;
		try {
			String url = "http://api.themoviedb.org/3/movie/"+_movieid+"/casts?api_key="
					+ Config.MDB.getKey();
			Log.v(TAG,url);
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
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
			datafromServer=sb.toString();
			Log.v("fromthread",datafromServer);
			Gson gson=new Gson();
			CastMovie obj = (CastMovie) gson.fromJson(datafromServer,CastMovie.class);
			//String presentpage=obj.page;
			List<Actors> theListactors=obj.cast;
			List<Crew> theListcrew=obj.crew;
			for(Actors diffActors:theListactors){
				HashMap<String,String> hm=new HashMap<String,String>();
				String id=diffActors.id;
				String character=diffActors.character;
				String name=diffActors.name;
				String profile_path=diffActors.profile_path;
				hm.put("id",id);
				hm.put("character",character);
				hm.put("name",name);
				hm.put("profilepath", profile_path);
				al.add(hm);
				hm=null;
			}
		} catch (MalformedURLException e) {
			Log.v(TAG,"malformedurl"+e.getClass()+"--"+e.getMessage());
		} catch(FileNotFoundException e){
			Log.v(TAG,"filenotfound"+e.getClass()+"--"+e.getMessage());
		}catch (IOException e) {
			Log.v(TAG,"ioex"+e.getClass()+"--"+e.getMessage());
		}finally {
		     urlConnection.disconnect();
		   }
				
		return al;
	}

}
