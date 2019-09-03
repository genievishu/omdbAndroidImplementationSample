package com.example.myimdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myimdb.actions.StartNewActivityAction;
import com.example.myimdb.adapters.OMDBSearchResultsAdapter;
import com.example.myimdb.controller.OMDBSearchController;
import com.example.myimdb.model.OMDBSearchResultContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {

    /** Views */
    @BindView(R.id.movie_search_view)
    SearchView mMovieSearchView;
    @BindView(R.id.movie_search_result_view)
    RecyclerView mMovieResultRecyclerView;

    /** Controllers */
    private OMDBSearchController mOMDBSearchController;
    private OMDBSearchResultsAdapter mOMDBSearchResultsAdapter;

    /** Page count */
    private int mPageCount = 1;
    private String mSearchCriteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ButterKnife.bind(this);

        // Initializing required objects
        mOMDBSearchController = new OMDBSearchController();
        mOMDBSearchResultsAdapter = new OMDBSearchResultsAdapter(new StartNewActivityAction() {
            @Override
            public void startNewActivityFromAdapter(Intent intent) {
                startActivity(intent);
            }
        });

        initializeViews();
    }

    private void initializeViews() {

        // Adding listener not supported by butter knife
        mMovieSearchView.setOnQueryTextListener(new OnMovieSearchedListener());

        // Adding adapter to recyclerview
        mMovieResultRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mMovieResultRecyclerView.setAdapter(mOMDBSearchResultsAdapter);

        // Adding infinite scroll listener
        mMovieResultRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    mPageCount++;
                    searchMoviesByPage(mPageCount);
                }
            }
        });

    }

    private void searchMoviesByPage(int pageNumber) {
        mOMDBSearchController.searchMoviesByName(mSearchCriteria, getApplicationContext().getString(R.string.movie), String.valueOf(pageNumber), new Callback<OMDBSearchResultContainer>() {
            @Override
            public void onResponse(Call<OMDBSearchResultContainer> call, Response<OMDBSearchResultContainer> response) {
                OMDBSearchResultContainer omdbSearchResultContainer = response.body();
                if (null!=omdbSearchResultContainer && null!=omdbSearchResultContainer.getSearchResults()) {
                    mOMDBSearchResultsAdapter.addSearchResults(omdbSearchResultContainer.getSearchResults());
                }
            }

            @Override
            public void onFailure(Call<OMDBSearchResultContainer> call, Throwable t) {
                Toast.makeText(HomePageActivity.this, R.string.movie_search_error_1, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (null!=imm) {
            imm.hideSoftInputFromWindow(mMovieSearchView.getWindowToken(), 0);
        }
    }

    /**
     * Private class to implement movie search functionalities
     */
    private class OnMovieSearchedListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String searchedTerm) {
            // Checking if search criteria has changed
            mOMDBSearchResultsAdapter.clearList();
            mSearchCriteria = searchedTerm.trim();
            mPageCount = 1;
            searchMoviesByPage(mPageCount);
            hideKeyboard();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    }



}
