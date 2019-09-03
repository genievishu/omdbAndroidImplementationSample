package com.example.myimdb.controller;

import com.example.myimdb.model.OMDBMovieDetail;
import com.example.myimdb.model.OMDBSearchResultContainer;
import com.example.myimdb.service.OmdbApi;
import com.example.myimdb.utils.AppUtils;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vishu on 02-09-2019
 */
public class OMDBSearchController  {

    // TODO : Move these to config files
    public static final String OMDB_BASE_URL = "http://www.omdbapi.com";
    public static final String OMDB_API_KEY = "32c3e78f";

    public void searchMoviesByName(String searchTerm, String type, String page, Callback<OMDBSearchResultContainer> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(AppUtils.getGson()))
                .build();

        OmdbApi omdbApi = retrofit.create(OmdbApi.class);
        omdbApi.searchIMDBMoviesByName(OMDB_API_KEY, searchTerm, type, page).enqueue(callback);
    }

    public void fetchMovieDetailsById(String omdbId, Callback<OMDBMovieDetail> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(AppUtils.getGson()))
                .build();
        OmdbApi omdbApi = retrofit.create(OmdbApi.class);
        omdbApi.searchIMDBMoviesById(OMDB_API_KEY, omdbId).enqueue(callback);


    }
}
