package com.hitman.musicx.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Song>> songsList=new MutableLiveData<>();
    private MutableLiveData<Song> currentSong=new MutableLiveData<>();

    public LiveData<List<Song>> getSongsList() {
        return songsList;
    }

    public void setSongsList(List<Song> songsList) {
        this.songsList.postValue(songsList);
    }

    public LiveData<Song> getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong.postValue(currentSong);
    }
}
