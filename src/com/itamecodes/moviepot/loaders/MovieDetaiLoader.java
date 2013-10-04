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
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.itamecodes.moviepot.config.Config;
import com.itamecodes.moviepot.jsonobjects.GenreObject;
import com.itamecodes.moviepot.jsonobjects.MovieInfo;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class MovieDetaiLoader extends WrappedAsyncTaskLoader<ArrayList<HashMap<String,String>>> {
	/*
	 * This class loads the youtube image thumbnail and the description of the movie
	 */
	private String _movieid;
	static final String TAG="MovieDetailLoader";
	public MovieDetaiLoader(Context c,String movieid) {
		super(c);
		this._movieid=movieid;
		Config config;
		
	}

	@Override
	public ArrayList<HashMap<String, String>> loadInBackground() {
		String url = "http://api.themoviedb.org/3/movie/"+_movieid+"?api_key="
				+ Config.MDB.getKey();
		Log.v(TAG,url);
		ArrayList<HashMap<String,String>> al=new ArrayList<HashMap<String,String>>();
		HttpURLConnection urlConnection=null;
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
			MovieInfo movieinfoobject=gson.fromJson(datafromServer, MovieInfo.class);
		
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("backdrop", movieinfoobject.backdrop_path);
			hm.put("homepage", movieinfoobject.homepage);
			hm.put("overview", movieinfoobject.overview);
			hm.put("caption", movieinfoobject.tagline);
			hm.put("title", movieinfoobject.title);
			hm.put("popularity", movieinfoobject.popularity);
			hm.put("vote_average", movieinfoobject.vote_average);
			hm.put("release_date", movieinfoobject.release_date);
			hm.put("imdb", movieinfoobject.imdb_id);
			List<GenreObject> thegenres=movieinfoobject.genres;
			StringBuilder sbuilder=new StringBuilder();
			for(GenreObject genreob:thegenres){
				//String id=genreob.id;
				String name=genreob.name;
				//sbuilder.append(id);
				//sbuilder.append("-");
				sbuilder.append(name);
				sbuilder.append(",");
			}
			String thegenrestringtemp=sbuilder.toString();
			if(isNotNullNotEmptyNotWhiteSpaceOnly(thegenrestringtemp)){
				String thegenrestring=thegenrestringtemp.substring(0,thegenrestringtemp.length()-1);
				hm.put("genres",thegenrestring);
			}else{
				hm.put("genres",thegenrestringtemp);
			}
			
			
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
	
	public static boolean isNotNullNotEmptyNotWhiteSpaceOnly(final String string)
	{
	   return string != null && !string.isEmpty() && !string.trim().isEmpty();
	}


}
