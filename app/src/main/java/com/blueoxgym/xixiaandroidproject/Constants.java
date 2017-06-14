package com.blueoxgym.xixiaandroidproject;

/**
 * Created by macbook on 5/27/17.
 */

public class Constants {
    public static final String UNSPLASH_ID=BuildConfig.UNSPLASH_ID;
    public static final String UNSPLASH_URL = "https://api.unsplash" +
            ".com/photos/random/?query=food&count=30";
    public static final String UNSPLASH_CLIENT_ID = "client_id";
    public static final String FIREBASE_CHILD_DESCRIBED_FOODS = "describedFoods" ;
    public static final String LAST_FOOD_SEARCH = "search";

    //Yelp
    public static final String YELP_CONSUMER_KEY = BuildConfig.YELP_CONSUMER_KEY;
    public static final String YELP_CONSUMER_SECRET = BuildConfig.YELP_CONSUMER_SECRET;
    public static final String YELP_TOKEN = BuildConfig.YELP_TOKEN;
    public static final String YELP_TOKEN_SECRET = BuildConfig.YELP_TOKEN_SECRET;
    public static final String YELP_BASE_URL = "https://api.yelp.com/v2/search?term=food";
    public static final String YELP_LOCATION_QUERY_PARAMETER = "location";
    public static final String PREFERENCES_LOCATION_KEY = "location";

}
