package com.itamecodes.moviepot.eventobjects;

public class ReviewsNeeded {
	private String _id;
	public ReviewsNeeded(String id){
		this._id=id;
	}
	public String getimdbId(){
		return _id;
	}
}
