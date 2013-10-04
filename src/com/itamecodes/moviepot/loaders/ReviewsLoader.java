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
import com.itamecodes.moviepot.jsonobjects.ProRev;
import com.itamecodes.moviepot.jsonobjects.ProfReviews;
import com.itamecodes.moviepot.jsonobjects.RatingsObject;
import com.itamecodes.moviepot.jsonobjects.ReviewObject;
import com.itamecodes.moviepot.utils.WrappedAsyncTaskLoader;

public class ReviewsLoader extends
		WrappedAsyncTaskLoader<ArrayList<HashMap<String, String>>> {
	/*
	 * This class loads the youtube image thumbnail and the description of the
	 * movie
	 */
	private String _imdbid;
	static final String TAG = "ReviewsLoader";

	public ReviewsLoader(Context c, String movieid) {
		super(c);
		this._imdbid = movieid;
		Config config;

	}

	@Override
	public ArrayList<HashMap<String, String>> loadInBackground() {
		String url = "http://api.rottentomatoes.com/api/public/v1.0/movie_alias.json?type=imdb&id="+_imdbid+"&apikey="+Config.Rotten.getKey();
		Log.v(TAG, url);
		ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
		HttpURLConnection urlConnection = null;
		String datafromServer=null;
		try {
			URL theurl = new URL(url);
			urlConnection = (HttpURLConnection) theurl.openConnection();
			int maxStale = 60 * 60 * 24; // tolerate 4-weeks stale
			urlConnection.addRequestProperty("Cache-Control", "max-stale="
					+ maxStale);
			urlConnection.setRequestProperty("Accept", "application/json");
			InputStream is = new BufferedInputStream(
					urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			datafromServer = sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			urlConnection.disconnect();
		}
		try{
		
			Log.v(TAG, datafromServer);
			Gson gson = new Gson();
			ReviewObject reviewOb = gson.fromJson(datafromServer,
					ReviewObject.class);
			RatingsObject ratingOb=reviewOb.ratings;
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("consensus", reviewOb.critics_consensus);
			hm.put("id", reviewOb.id);
			hm.put("criticsrating", ratingOb.critics_rating);
			hm.put("criticscore", ratingOb.critics_score);
			hm.put("audrating", ratingOb.audience_rating);
			hm.put("audscore",ratingOb.audience_score);
			al.add(hm);
			if(isNotNullNotEmptyNotWhiteSpaceOnly(reviewOb.id)){
				String newurl="http://api.rottentomatoes.com/api/public/v1.0/movies/"+reviewOb.id+"/reviews.json?apikey=u4rbs8vpz5kdgjnhdqmcrp88";
				try {
					URL theurl = new URL(newurl);
					urlConnection = (HttpURLConnection) theurl.openConnection();
					int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
					urlConnection.addRequestProperty("Cache-Control", "max-stale="
							+ maxStale);
					urlConnection.setRequestProperty("Accept", "application/json");
					InputStream is = new BufferedInputStream(
							urlConnection.getInputStream());
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
					datafromServer = sb.toString();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					urlConnection.disconnect();
				}
				Log.v(TAG, datafromServer);
				ProfReviews profrev = gson.fromJson(datafromServer,
						ProfReviews.class);
				List<ProRev> prorev=profrev.reviews;
				
				for(ProRev rev:prorev){
					HashMap<String, String> newhm = new HashMap<String, String>();
					newhm.put("critic",rev.critic);
					newhm.put("freshness",rev.freshness);
					newhm.put("publication",rev.publication);
					newhm.put("quote", rev.quote);
					al.add(newhm);
				}
				
			}
		}catch(Exception e){
			
		}
			

			
			return al;
	
	
	}

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnly(final String string) {
		return string != null && !string.isEmpty() && !string.trim().isEmpty();
	}

}
