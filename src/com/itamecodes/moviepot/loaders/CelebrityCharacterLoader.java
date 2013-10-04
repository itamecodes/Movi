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
import com.itamecodes.moviepot.jsonobjects.CelebrityCharacter;
import com.itamecodes.moviepot.jsonobjects.Characters;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class CelebrityCharacterLoader extends WrappedAsyncTaskLoader<ArrayList<HashMap<String,String>>> {
	private String _celebid;
	static final String TAG="CelebrityCharacterLoader";
	public CelebrityCharacterLoader(Context context,String celebid) {
		
		super(context);
		Config config;
		this._celebid=celebid;
	}

	@Override
	public ArrayList<HashMap<String,String>> loadInBackground() {
		String datafromServer = null;
		ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
		HttpURLConnection urlConnection=null;
		
		try {
			String url = "http://api.themoviedb.org/3/person/"+_celebid+"/credits?api_key="
					+ Config.MDB.getKey();
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
			CelebrityCharacter obj = (CelebrityCharacter) gson.fromJson(datafromServer,CelebrityCharacter.class);
			List<Characters> theListcharacter=obj.cast;
			for(Characters diffActors:theListcharacter){
				HashMap<String,String> hm=new HashMap<String,String>();
				String character=diffActors.character;
				String moviename=diffActors.title;
				String poster_path=diffActors.poster_path;
				String id=diffActors.id;
				hm.put("character",character);
				hm.put("name",moviename);
				hm.put("posterpath", poster_path);
				hm.put("movid", id);
				al.add(hm);
				hm=null;
			}
		} catch (MalformedURLException e) {
			Log.v(TAG,"malformedurl"+e.getClass()+"--"+e.getMessage());
		} catch(FileNotFoundException e){
			Log.v(TAG,"filenotfound"+e.getClass()+"--"+e.getMessage());
		}catch (IOException e) {
			Log.v(TAG,"ioex"+e.getClass()+"--"+e.getMessage());
		}finally{
			urlConnection.disconnect();
		}
				
		return al;
	}

}
