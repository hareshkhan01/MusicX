package com.hitman.musicx.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hitman.musicx.R;
import com.hitman.musicx.adapter.SongRecyclerViewAdapter;
import com.hitman.musicx.data.Repository;
import com.hitman.musicx.model.SharedViewModel;
import com.hitman.musicx.model.Song;

import java.util.List;

public class SongsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongRecyclerViewAdapter recyclerViewAdapter;
    private List<Song> songList;
    private SharedViewModel sharedViewModel;
    public SongsFragment() {
        // Required empty public constructor
    }

    public static SongsFragment newInstance() {
        SongsFragment fragment = new SongsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView=view.findViewById(R.id.songs_recyclerview);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        if(sharedViewModel.getSongsList().getValue()!=null){
            sharedViewModel.getSongsList().observe(requireActivity(), songList -> {
                recyclerViewAdapter=new SongRecyclerViewAdapter(getContext(),songList);
                recyclerView.setAdapter(recyclerViewAdapter);
            });
        }








    }
}