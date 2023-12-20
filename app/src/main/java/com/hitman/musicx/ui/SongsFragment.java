package com.hitman.musicx.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitman.musicx.R;
import com.hitman.musicx.adapter.SongRecyclerViewAdapter;

import com.hitman.musicx.model.SharedViewModel;
import com.hitman.musicx.model.Song;
import com.hitman.musicx.model.SongViewModel;
import com.hitman.musicx.player.MusicPlayer;


import java.util.ArrayList;


public class SongsFragment extends Fragment implements SongRecyclerViewAdapter.OnSongClickListener{
    private RecyclerView recyclerView;
    private SongRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Song> songList;
    private SharedViewModel sharedViewModel;
    private CardView floatingCardView;

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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.songs_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedViewModel= new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        SongViewModel.getSongsList().observe(getViewLifecycleOwner(), songArrayList -> {

            recyclerViewAdapter= new SongRecyclerViewAdapter(songArrayList,this,requireContext());
            recyclerView.setAdapter(recyclerViewAdapter);
            sharedViewModel.setSongsList(songArrayList);
            songList=songArrayList;
        });



    }

    @Override
    public void onSongClick(Song song,int position) {
        sharedViewModel.setCurrentSong(song);
        sharedViewModel.setCurrentSongPosition(position);
        sharedViewModel.setIsMusicPlaying(true);
        if (MusicPlayer.isAnyMusicPlaying()){
            MusicPlayer.pauseCurrentMusic();
            MusicPlayer.releaseMediaPlayer();
            MusicPlayer.playMusic(song);
        }
        else {
            MusicPlayer.playMusic(song);
        }
    }
}