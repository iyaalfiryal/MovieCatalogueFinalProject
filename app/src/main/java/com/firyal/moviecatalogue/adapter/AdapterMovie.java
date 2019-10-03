package com.firyal.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.activity.detail.DetailMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.ViewHolder> {

    private Context context;
    private List<ResultsItemMovie> resultsItemMovies;

    public AdapterMovie(Context context, List<ResultsItemMovie> resultsItemMovies) {
        this.context = context;
        this.resultsItemMovies = resultsItemMovies;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_movie, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(resultsItemMovies.get(position).getTitle());
        holder.time.setText(resultsItemMovies.get(position).getReleaseDate());
        Glide.with(context)
                .load(Constant.URLIMAGE + resultsItemMovies.get(position)
                        .getPosterPath()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailMovie.class);
                i.putExtra(Constant.EXTRA_MOVIE, resultsItemMovies.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsItemMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
        }
    }
}
