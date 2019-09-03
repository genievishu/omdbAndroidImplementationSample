package com.example.myimdb;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myimdb.controller.OMDBSearchController;
import com.example.myimdb.model.OMDBMovieDetail;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    /** Views */
    @BindView(R.id.movie_content_poster)
    ImageView mMoviePosterImageView;
    @BindView(R.id.movie_content_title)
    TextView mMovieTitleTextView;
    @BindView(R.id.movie_content_description)
    TextView mMovieDescriptionTextView;
    @BindView(R.id.movie_content_progressbar)
    ProgressBar mLoader;
    @BindView(R.id.movie_content_released_description)
    TextView mMovieReleaseDateTextView;
    @BindView(R.id.movie_content_runtime_description)
    TextView mMovieRuntimeTextView;
    @BindView(R.id.movie_content_genre_description)
    TextView mMovieGenreTextView;
    @BindView(R.id.movie_content_language_description)
    TextView mMovieLanguageTextView;
    @BindView(R.id.movie_crew_director_description)
    TextView mMovieCrewDirectorTextView;
    @BindView(R.id.movie_crew_writers_description)
    TextView mMovieCrewWriterTextView;
    @BindView(R.id.movie_crew_actors_description)
    TextView mMovieCrewActorsTextView;
    @BindView(R.id.movie_content_rating_description)
    TextView mMovieRatingTextView;
    @BindView(R.id.movie_content_rating)
    RatingBar mMovieRatingBar;

    /** Model */
    private OMDBMovieDetail mOMDBMovieDetail;

    /** Controllers */
    private OMDBSearchController mOMDBSearchController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mOMDBSearchController = new OMDBSearchController();

        initializeViews();

        if (null!=getIntent() && null!=getIntent().getStringExtra(getString(R.string.intent_imdbid))) {
            searchMovieDetailsById(getIntent().getStringExtra(getString(R.string.intent_imdbid)));
        }
        else {
            Toast.makeText(MovieDetailsActivity.this, R.string.movie_search_error_2, Toast.LENGTH_LONG).show();
        }

    }

    private void initializeViews() {

    }

    private void searchMovieDetailsById(String omdbId) {
        mOMDBSearchController.fetchMovieDetailsById(omdbId, new Callback<OMDBMovieDetail>() {
            @Override
            public void onResponse(Call<OMDBMovieDetail> call, Response<OMDBMovieDetail> response) {
                OMDBMovieDetail omdbMovieDetail = response.body();
                if (null!=omdbMovieDetail) {
                    mOMDBMovieDetail = omdbMovieDetail;
                    handleMovieDetailsLoaded();
                }
                else {
                    Toast.makeText(MovieDetailsActivity.this, R.string.movie_search_error_2, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OMDBMovieDetail> call, Throwable t) {
                // TODO : Add logging
                Toast.makeText(MovieDetailsActivity.this, R.string.movie_search_error_2, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleMovieDetailsLoaded() {
        mLoader.setVisibility(View.GONE);
        mMovieTitleTextView.setText(new StringBuilder().append(mOMDBMovieDetail.getTitle()).append(getString(R.string.open_round_bracket)).append(mOMDBMovieDetail.getYear()).append(getString(R.string.close_round_bracket)));
        mMovieDescriptionTextView.setText(mOMDBMovieDetail.getPlot());
        mMovieReleaseDateTextView.setText(mOMDBMovieDetail.getReleased());
        mMovieRuntimeTextView.setText(mOMDBMovieDetail.getRuntime());
        mMovieGenreTextView.setText(mOMDBMovieDetail.getGenre());
        mMovieLanguageTextView.setText(mOMDBMovieDetail.getLanguage());
        mMovieCrewDirectorTextView.setText(mOMDBMovieDetail.getDirector());
        mMovieCrewWriterTextView.setText(mOMDBMovieDetail.getWriter());
        mMovieCrewActorsTextView.setText(mOMDBMovieDetail.getActors());
        mMovieRatingBar.setRating(mOMDBMovieDetail.getImdbRating());
        mMovieRatingTextView.setText(new StringBuilder().append(mOMDBMovieDetail.getImdbRating()).append(getString(R.string.forward_slash)).append(getString(R.string.ten)));
        mMovieRatingBar.setIsIndicator(true);
        Picasso.get().cancelRequest(mMoviePosterImageView);
        Picasso.get().load(mOMDBMovieDetail.getPoster()).into(mMoviePosterImageView);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
