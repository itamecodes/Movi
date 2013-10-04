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
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.itamecodes.moviepot.config.Config;
import com.itamecodes.moviepot.jsonobjects.MovieImages;
import com.itamecodes.moviepot.jsonobjects.Posters;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class MovieImageGalleryLoader extends WrappedAsyncTaskLoader<ArrayList<String>> {
	private String _movieid;
	static final String TAG="MovieImageGalleryLoader";
	public MovieImageGalleryLoader(Context context,String movieid) {
		
		super(context);
		Config config;
		this._movieid=movieid;
	}

	@Override
	public ArrayList<String> loadInBackground() {
		String datafromServer = null;
		ArrayList<String> al = new ArrayList<String>();
		HttpURLConnection urlConnection=null;
		
		try {
			String url = "http://api.themoviedb.org/3/movie/"+_movieid+"/images?api_key="
					+ Config.MDB.getKey();
			Log.v(TAG,url);
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
			int maxStale = 60 * 60 * 24; // tolerate 4-weeks stale
			urlConnection.addRequestProperty("Cache-Control", "max-stale=" +
			maxStale);
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
			MovieImages obj = (MovieImages) gson.fromJson(datafromServer,MovieImages.class);
			//String presentpage=obj.page;
			List<Posters> theList=obj.posters;
			for(Posters theposter:theList){
				
				String poster=theposter.file_path;
				
				al.add(poster);
				poster=null;
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
