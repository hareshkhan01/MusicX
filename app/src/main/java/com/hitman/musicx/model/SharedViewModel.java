package com.hitman.musicx.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Song>> songsList=new MutableLiveData<>();
    private final MutableLiveData<Song> currentSong=new MutableLiveData<>();
    private final MutableLiveData<Integer> currentSongPosition=new MutableLiveData<>();
    private final MutableLiveData<Boolean> isMusicPlaying=new MutableLiveData<>();

    public void setCurrentSongPosition(int position){
        currentSongPosition.postValue(position);
    }
    public LiveData<Integer> getCurrentSongPosition() {
        return currentSongPosition;
    }

    public void setIsMusicPlaying(Boolean b){
        this.isMusicPlaying.postValue(b);
    }
    public LiveData<Boolean> getIsMusicPlaying(){
        return isMusicPlaying;
    }

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
