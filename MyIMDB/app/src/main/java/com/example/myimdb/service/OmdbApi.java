package com.example.myimdb.service;

import com.example.myimdb.model.OMDBMovieDetail;
import com.example.myimdb.model.OMDBSearchResultContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vishu on 02-09-2019
 */
public interface OmdbApi {

    @GET("/")
    Call<OMDBSearchResultContainer> searchIMDBMoviesByName(
            @Query("apiKey") String apiKey,
            @Query("s") String term,
            @Query("type") String type,
            @Query("page") String page
    );

    @GET("/")
    Call<OMDBMovieDetail> searchIMDBMoviesById(
            @Query("apiKey") String apiKey,
            @Query("i") String omdbId
    );

}
