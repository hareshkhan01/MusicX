package com.hitman.musicx.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hitman.musicx.data.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SongViewModel extends AndroidViewModel {
    private final Repository repository;
    private static LiveData<ArrayList<Song>> songList;
    public SongViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        songList= repository.getSongList();
    }
    public static LiveData<ArrayList<Song>> getSongsList(){
        return songList;
    }
}
