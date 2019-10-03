package com.firyal.moviecatalogue.favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firyal.moviecatalogue.R;
import com.firyal.moviecatalogue.adapter.AdapterFavTv;
import com.firyal.moviecatalogue.adapter.AdapterMovie;
import com.firyal.moviecatalogue.adapter.AdapterTv;
import com.firyal.moviecatalogue.data.tvshow.DatabaseTv;
import com.firyal.moviecatalogue.model.ResultsItemTv;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvshowFragment extends Fragment {

    DatabaseTv databaseTv;
    private List<ResultsItemTv> resultsItemTvs = new ArrayList<>();
    private AdapterFavTv adapterTv;
    private RecyclerView rv;
    private ProgressBar pbtv;


    public FavTvshowFragment() {
        // Required empty public constructor
    }
    private void setFragmentManager(FragmentManager fragmentManager) {
    }

    public static FavTvshowFragment newInstance(FragmentManager fragmentManager) {
        FavTvshowFragment fragment = new FavTvshowFragment();
        fragment.setFragmentManager(fragmentManager);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseTv = DatabaseTv.getDatabaseTv(getActivity());
        rv = view.findViewById(R.id.favTv);
        pbtv = view.findViewById(R.id.pbtv);
        getFavTv();
        setRecyclerView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeFatTv((long)viewHolder.itemView.getTag());
                resultsItemTvs.remove(resultsItemTvs.get(viewHolder.getAdapterPosition()));
                adapterTv.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(rv);
    }

    private void removeFatTv(long tag) {
        databaseTv = DatabaseTv.getDatabaseTv(getActivity());
        databaseTv.tvshowDao().deleteByIdTv(tag);
        Toast.makeText(getActivity(), R.string.deleted, Toast.LENGTH_SHORT).show();
    }

    private void showLoading(Boolean state) {
        if (state){
            pbtv.setVisibility(View.VISIBLE);
        }else {
            pbtv.setVisibility(View.GONE);
        }

    }


    private void setRecyclerView() {
        resultsItemTvs = databaseTv.tvshowDao().getFavoriteTvshow();
        if (adapterTv == null){
            adapterTv = new AdapterFavTv(getActivity(), resultsItemTvs);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rv.setAdapter(adapterTv);
        }else {
            adapterTv.notifyDataSetChanged();
        }

    }

    private void getFavTv() {
        if(databaseTv.tvshowDao().getFavoriteTvshow()== null){
            Toast.makeText(getActivity(), getString(R.string.nofav), Toast.LENGTH_SHORT).show();
            showLoading(false);
        }else {
            databaseTv.tvshowDao().getFavoriteTvshow();
            showLoading(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
