package com.hitman.musicx.ui;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hitman.musicx.R;
import com.hitman.musicx.adapter.SongRecyclerViewAdapter;
import com.hitman.musicx.data.Repository;
import com.hitman.musicx.model.SharedViewModel;
import com.hitman.musicx.model.Song;
import com.hitman.musicx.player.MusicPlayer;

import java.util.List;
import java.util.Objects;

public class SongsFragment extends Fragment implements SongRecyclerViewAdapter.OnSongClickListener{
    private RecyclerView recyclerView;
    private SongRecyclerViewAdapter recyclerViewAdapter;
    private List<Song> songList;
    private SharedViewModel sharedViewModel;
    Repository repository;
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

        repository=new Repository(requireContext());
        recyclerView=view.findViewById(R.id.songs_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        songList=repository.getSongList();
        sharedViewModel.setSongsList(songList);
        if(songList!=null){
            recyclerViewAdapter=new SongRecyclerViewAdapter(songList,this,requireContext());
            recyclerView.setAdapter(recyclerViewAdapter);
        }


    }

    @Override
    public void onSongClick(Song song) {
        sharedViewModel.setCurrentSong(song);
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