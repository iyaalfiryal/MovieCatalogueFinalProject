package com.firyal.moviecatalogue.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firyal.moviecatalogue.Constant;
import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.activity.detail.DetailMovie;
import com.firyal.moviecatalogue.data.movie.DatabaseMovie;
import com.firyal.moviecatalogue.favorite.FavMovieFragment;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterFavMovie extends RecyclerView.Adapter<AdapterFavMovie.ViewHolder> {

    private Context context;
    private List<ResultsItemMovie> resultsItemMovies;

    public AdapterFavMovie(Context context, List<ResultsItemMovie> resultsItemMovies) {
        this.context = context;
        this.resultsItemMovies = resultsItemMovies;
    }


    @NonNull
    @Override
    public AdapterFavMovie.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavMovie.ViewHolder holder, int position) {
        holder.title.setText(resultsItemMovies.get(position).getTitle());
        holder.time.setText(resultsItemMovies.get(position).getReleaseDate());
        Glide.with(context).load(Constant.URLIMAGE + resultsItemMovies.get(position).getPosterPath()).into(holder.img);
        long id = resultsItemMovies.get(position).getId();

        holder.itemView.setTag(id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovie.class);
                intent.putExtra(Constant.EXTRA_MOVIE, resultsItemMovies.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert;
                alert = new AlertDialog.Builder(context);
                alert.setCancelable(true);
                alert.setTitle(R.string.hapus);
                alert.setPositiveButton(R.string.ya, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DatabaseMovie databaseMovie = DatabaseMovie.getDatabaseMovie(context);
                        databaseMovie.movieDao().deleteByIdMovie(resultsItemMovies.get(holder.getAdapterPosition()).getId());
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show();
                        resultsItemMovies.remove(resultsItemMovies.get(holder.getAdapterPosition()));
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });
                alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                return true;
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsItemMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            img = itemView.findViewById(R.id.img);
        }
    }
}
