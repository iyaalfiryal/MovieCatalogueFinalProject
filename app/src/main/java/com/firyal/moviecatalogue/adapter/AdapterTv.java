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
import com.firyal.moviecatalogue.activity.detail.DetailTv;
import com.firyal.moviecatalogue.model.ResultsItemTv;

import java.util.List;

public class AdapterTv extends RecyclerView.Adapter<AdapterTv.ViewHolder> {

    private Context context;
    private List<ResultsItemTv>resultsItemTvs;

    public AdapterTv(Context context, List<ResultsItemTv> resultsItemTvs) {
        this.context = context;
        this.resultsItemTvs = resultsItemTvs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_tv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title2.setText(resultsItemTvs.get(position).getName());
        holder.time2.setText(resultsItemTvs.get(position).getFirstAirDate());
        Glide.with(context).load(Constant.URLIMAGE + resultsItemTvs.get(position).getBackdropPath())
                .into(holder.img2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailTv.class);
                intent.putExtra(Constant.EXTRA_MOVIE, resultsItemTvs.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsItemTvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img2;
        TextView title2, time2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img2 = itemView.findViewById(R.id.img2);
            title2 = itemView.findViewById(R.id.title2);
            time2 = itemView.findViewById(R.id.time2);
        }
    }
}
