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
import com.itamecodes.moviepot.jsonobjects.DifferentMovies;
import com.itamecodes.moviepot.jsonobjects.MoviesList;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class SimilarMoviesLoader extends WrappedAsyncTaskLoader<ArrayList<HashMap<String,String>>> {
	String page;
	String movieid;
	static final String TAG="SimilarMoviesLoader";
	public SimilarMoviesLoader(Context context,String pageno,String id) {
		super(context);
		Config config;
		page=pageno;
		movieid=id;
	}

	@Override
	public ArrayList<HashMap<String,String>> loadInBackground() {
		String datafromServer = null;
		ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
		
		
		try {
			String url = "http://api.themoviedb.org/3/movie/"+movieid+"/similar_movies?api_key="
					+ Config.MDB.getKey() + "&page="+page;
			Log.v(TAG,url);
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
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
			MoviesList obj = (MoviesList) gson.fromJson(datafromServer,MoviesList.class);
			String totalpages=obj.total_pages;
			//String presentpage=obj.page;
			List<DifferentMovies> theList=obj.results;
			for(DifferentMovies diffMovies:theList){
				HashMap<String,String> hm=new HashMap<String,String>();
				String id=diffMovies.id;
				String title=diffMovies.title;
				String poster=diffMovies.poster_path;
				hm.put("id",id);
				hm.put("title",title);
				hm.put("poster",poster);
				hm.put("totalpages", totalpages);
				al.add(hm);
			}
		} catch (MalformedURLException e) {
			Log.v(TAG,"malformedurl"+e.getClass()+"--"+e.getMessage());
		} catch(FileNotFoundException e){
			Log.v(TAG,"filenotfound"+e.getClass()+"--"+e.getMessage());
		}catch (IOException e) {
			Log.v(TAG,"ioex"+e.getClass()+"--"+e.getMessage());
		}
				
		return al;
	}

}
