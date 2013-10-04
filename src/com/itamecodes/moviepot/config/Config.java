package com.itamecodes.moviepot.config;

public enum Config {
	MDB("b1a2d97608f210126674df510c71ab52"),TRAKT("861fd1bfe6a2ab3520e505367f55815b"),Rotten("u4rbs8vpz5kdgjnhdqmcrp88");
	private String apiKey;
	Config(String key){
		apiKey=key;
	}
	public String getKey(){
		return apiKey;
	}
	
}
