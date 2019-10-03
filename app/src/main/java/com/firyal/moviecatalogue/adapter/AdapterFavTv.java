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
import com.firyal.moviecatalogue.activity.detail.DetailTv;
import com.firyal.moviecatalogue.data.movie.DatabaseMovie;
import com.firyal.moviecatalogue.data.tvshow.DatabaseTv;
import com.firyal.moviecatalogue.model.ResultsItemTv;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterFavTv extends RecyclerView.Adapter<AdapterFavTv.ViewHolder> {

    private Context context;
    private List<ResultsItemTv> resultsItemTvs;

    public AdapterFavTv(Context context, List<ResultsItemTv> resultsItemTvs) {
        this.context = context;
        this.resultsItemTvs = resultsItemTvs;
    }

    @NonNull
    @Override
    public AdapterFavTv.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_tv, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavTv.ViewHolder holder, int position) {
        holder.title2.setText(resultsItemTvs.get(position).getName());
        holder.time2.setText(resultsItemTvs.get(position).getFirstAirDate());
        Glide.with(context).load(Constant.URLIMAGE + resultsItemTvs.get(position).getPosterPath()).into(holder.img2);
        long id = resultsItemTvs.get(position).getId();

        holder.itemView.setTag(id);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTv.class);
                intent.putExtra(Constant.EXTRA_MOVIE, resultsItemTvs.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final AlertDialog.Builder alert;
                alert = new AlertDialog.Builder(context);
                alert.setCancelable(true);
                alert.setTitle(R.string.hapus);
                alert.setPositiveButton(R.string.ya, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DatabaseTv databaseTv = DatabaseTv.getDatabaseTv(context);
                        databaseTv.tvshowDao().deleteByIdTv(resultsItemTvs.get(holder.getAdapterPosition()).getId());
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show();
                        resultsItemTvs.remove(resultsItemTvs.get(holder.getAdapterPosition()));
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
        return resultsItemTvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title2, time2;
        ImageView img2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title2 = itemView.findViewById(R.id.title2);
            time2 = itemView.findViewById(R.id.time2);
            img2 = itemView.findViewById(R.id.img2);
        }
    }
}
