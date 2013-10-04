package com.itamecodes.moviepot.mainapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.itamecodes.moviepot.R;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
	static private final String DEVELOPERKEY="AIzaSyDWUL8neP3ZEZCttGa2YYCEP5ZPtiMDALA";
	private String video;
	
	@Override
	public void onCreate(Bundle icic){
		super.onCreate(icic);
		setContentView(R.layout.videoplaylayout);
		video=getIntent().getStringExtra("videoid");
		YouTubePlayerView youTubeView=(YouTubePlayerView)findViewById(R.id.youtube_view);
		youTubeView.initialize(DEVELOPERKEY, this);
		
	}

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult error) {
		 Toast.makeText(this, "Please update your YoutubeApp by going to Google Play",
				 Toast.LENGTH_LONG).show();		
	}

	@Override
	public void onInitializationSuccess(Provider provider, final YouTubePlayer player,
			boolean wasRestored) {

		player.loadVideo(video);
		
	}
	
	
	
}
