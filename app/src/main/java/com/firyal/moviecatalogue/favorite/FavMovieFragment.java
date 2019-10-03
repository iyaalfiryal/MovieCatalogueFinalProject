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
import com.firyal.moviecatalogue.adapter.AdapterFavMovie;
import com.firyal.moviecatalogue.adapter.AdapterMovie;
import com.firyal.moviecatalogue.data.movie.DatabaseMovie;
import com.firyal.moviecatalogue.model.ResultsItemMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {

    DatabaseMovie databaseMovie;
    private List<ResultsItemMovie> resultsItemMovies = new ArrayList<>();
    private AdapterFavMovie adapterMovie;
    private RecyclerView recyclerView;
    private ProgressBar pb;
    public FavMovieFragment() {
        // Required empty public constructor
    }
    private void setFragmentManager(FragmentManager fragmentManager) {
    }

    public static FavMovieFragment newInstance(FragmentManager fragmentManager) {
        FavMovieFragment fragment = new FavMovieFragment();
        fragment.setFragmentManager(fragmentManager);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseMovie = DatabaseMovie.getDatabaseMovie(getActivity());
        pb = view.findViewById(R.id.pbFavMovie);
        recyclerView = view.findViewById(R.id.favMovie);
        getFavMovie();
        setRecyclerView();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeFavorite((long)viewHolder.itemView.getTag());
                resultsItemMovies.remove(resultsItemMovies.get(viewHolder.getAdapterPosition()));
                adapterMovie.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void showLoad(Boolean state){
        if (state){
            pb.setVisibility(View.VISIBLE);
        }else {
            pb.setVisibility(View.GONE);
        }
    }
    private void removeFavorite(long tag){
        databaseMovie = DatabaseMovie.getDatabaseMovie(getActivity());
        databaseMovie.movieDao().deleteByIdMovie(tag);
        Toast.makeText(getActivity(), R.string.deleted, Toast.LENGTH_SHORT).show();
    }

    public void getFavMovie(){
        if (databaseMovie.movieDao().getFavoriteMovie() == null){
            Toast.makeText(getActivity(), getString(R.string.nofav), Toast.LENGTH_SHORT).show();
            showLoad(false);
        }else {
            databaseMovie.movieDao().getFavoriteMovie();
            showLoad(false);
        }

    }

    public void setRecyclerView(){
        resultsItemMovies = databaseMovie.movieDao().getFavoriteMovie();
        if (adapterMovie == null){
            adapterMovie = new AdapterFavMovie(getActivity(), resultsItemMovies);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapterMovie);

        }else {
            adapterMovie.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
