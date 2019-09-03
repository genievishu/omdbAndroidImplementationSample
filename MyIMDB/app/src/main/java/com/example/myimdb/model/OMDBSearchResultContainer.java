package com.example.myimdb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * Created by Vishu on 02-09-2019
 */
public class OMDBSearchResultContainer {

    public static final String RESULT = "Search";
    @SerializedName(RESULT)
    private List<OMDBMovieDetail> searchResults;

    public List<OMDBMovieDetail> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<OMDBMovieDetail> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OMDBSearchResultContainer)) return false;
        OMDBSearchResultContainer that = (OMDBSearchResultContainer) o;
        return Objects.equals(getSearchResults(), that.getSearchResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSearchResults());
    }

    @Override
    public String toString() {
        return "OMDBSearchResultContainer{" +
                "searchResults=" + searchResults +
                '}';
    }
}
