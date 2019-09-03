package com.example.myimdb.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myimdb.MovieDetailsActivity;
import com.example.myimdb.R;
import com.example.myimdb.actions.StartNewActivityAction;
import com.example.myimdb.model.OMDBMovieDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishu on 02-09-2019
 */
public class OMDBSearchResultsAdapter extends RecyclerView.Adapter<OMDBSearchResultsAdapter.OMDBSearchResultElementViewHolder> {

    private List<OMDBMovieDetail> omdbMovieDetails;
    private int VIEW_TYPE_FIRST = 0;
    private int VIEW_TYPE_LAST = 1;
    private int VIEW_TYPE_MIDDLE = 2;

    private StartNewActivityAction mNewActivityAction;

    public OMDBSearchResultsAdapter(StartNewActivityAction newActivityAction) {
        this.mNewActivityAction = newActivityAction;
        this.omdbMovieDetails = new ArrayList<>(200);
    }

    @NonNull
    @Override
    public OMDBSearchResultElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = null;
        if (viewType == VIEW_TYPE_MIDDLE) {
            rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.omdb_search_result_view, parent, false);
        }
        else if (viewType == VIEW_TYPE_LAST) {
            rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.omdb_search_result_view_bottom, parent, false);
        }
        else {
            rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.omdb_search_result_view_top, parent, false);
        }
        return new OMDBSearchResultElementViewHolder(rootView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 1 && position != omdbMovieDetails.size()-1) {
            return VIEW_TYPE_MIDDLE;
        }
        else if (position == omdbMovieDetails.size()-1) {
            return VIEW_TYPE_LAST;
        }
        else {
            return VIEW_TYPE_FIRST;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull OMDBSearchResultElementViewHolder holder, int position) {

        holder.mMovieTitleTextView.setText(omdbMovieDetails.get(position).getTitle());
        holder.mMovieYearTextView.setText(omdbMovieDetails.get(position).getYear());
        Picasso.get().cancelRequest(holder.mMoviePosterImageView);
        Picasso.get().load(omdbMovieDetails.get(position).getPoster()).into(holder.mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return omdbMovieDetails.size();
    }

    public void addSearchResults(List<OMDBMovieDetail> searchResults) {
        this.omdbMovieDetails.addAll(searchResults);
        notifyItemInserted(omdbMovieDetails.size() - searchResults.size());
    }

    public void clearList() {
        this.omdbMovieDetails.clear();
        this.notifyDataSetChanged();
    }

    /**
     * Private class to hold view for each result
     */
    public class OMDBSearchResultElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mMoviePosterImageView;
        private TextView mMovieTitleTextView;
        private TextView mMovieYearTextView;

        public OMDBSearchResultElementViewHolder(View itemView) {
            super(itemView);
            this.mMovieTitleTextView = itemView.findViewById(R.id.omdb_movie_title);
            this.mMoviePosterImageView = itemView.findViewById(R.id.omdb_movie_poster);
            this.mMovieYearTextView = itemView.findViewById(R.id.omdb_movie_year);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (-1!=getAdapterPosition()) {
                Intent showMovieDetailsIntent = new Intent(view.getContext(), MovieDetailsActivity.class);
                showMovieDetailsIntent.putExtra(view.getContext().getString(R.string.intent_imdbid), omdbMovieDetails.get(getAdapterPosition()).getImdbId());
                mNewActivityAction.startNewActivityFromAdapter(showMovieDetailsIntent);
            }
        }
    }

}
