package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Movie> movies;
    private final int normMovie = 0, popMovie = 1;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    public int getItemViewType(int position){
        if(Double.valueOf(movies.get(position).getRating()) > 5.0){
            return popMovie;
        } else{
            return normMovie;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder movieView = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case normMovie:
                View normView = inflater.inflate(R.layout.item_movie, parent, false);
                movieView = new ViewHolderNormMovie(normView);
                break;
            case popMovie:
                View popView = inflater.inflate(R.layout.item_movie_popular, parent, false);
                movieView = new ViewHolderPopMovie(popView);
                break;
        }
        //View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return movieView;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()){
            case normMovie:
                ViewHolderNormMovie viewHolderNorm = (ViewHolderNormMovie) holder;
                bindNormMovie(viewHolderNorm, position);
                break;
            case popMovie:
                ViewHolderPopMovie viewHolderPopMovie = (ViewHolderPopMovie) holder;
                bindPopMovie(viewHolderPopMovie, position);
                break;

        }
        //Movie movie = movies.get(position);
        //holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolderNormMovie extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolderNormMovie(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }
    }

    public class ViewHolderPopMovie extends RecyclerView.ViewHolder {

        TextView tvTitlePop;
        TextView tvOverviewPop;
        ImageView ivPosterPop;
        RelativeLayout container;

        public ViewHolderPopMovie(@NonNull View itemView) {
            super(itemView);
            tvTitlePop = itemView.findViewById(R.id.tvTitlePop);
            tvOverviewPop = itemView.findViewById(R.id.tvOverviewPop);
            ivPosterPop = itemView.findViewById(R.id.ivPosterPop);
            container = itemView.findViewById(R.id.container);
        }
    }



    private void bindPopMovie(ViewHolderPopMovie viewHolderPopMovie, int position) {
        final Movie movie = movies.get(position);
        TextView tvTitlePop = viewHolderPopMovie.tvTitlePop;
        TextView tvOverViewPop = viewHolderPopMovie.tvOverviewPop;
        ImageView ivPosterPop = viewHolderPopMovie.ivPosterPop;
        RelativeLayout container = viewHolderPopMovie.container;
        tvTitlePop.setText(movie.getTitle());
        tvOverViewPop.setText(movie.getOverview());
        String imageUrl;
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUrl = movie.getBackdropPath();
        } else {
            imageUrl = movie.getPosterPath();
        }

        Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_placeholder).transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(15))).into(ivPosterPop);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movie));
                context.startActivity(i);
                }
        });

        }

    private void bindNormMovie(ViewHolderNormMovie viewHolderNormMovie, int position) {
        final Movie movie = movies.get(position);
        TextView tvTitle = viewHolderNormMovie.tvTitle;
        TextView tvOverView = viewHolderNormMovie.tvOverview;
        ImageView ivPoster = viewHolderNormMovie.ivPoster;
        RelativeLayout container = viewHolderNormMovie.container;
        tvTitle.setText(movie.getTitle());
        tvOverView.setText(movie.getOverview());
        String imageUrl;
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUrl = movie.getBackdropPath();
        } else {
            imageUrl = movie.getPosterPath();
        }

        Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_placeholder).transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(15))).into(ivPoster);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movie));
                context.startActivity(i);
            }
        });

    }
}

