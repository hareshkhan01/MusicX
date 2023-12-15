package com.hitman.musicx.model;

import android.media.Image;
import android.net.Uri;

public class Song {
    private String songName; // Song title or the name
    private String artistName; // Artist Name
    private String path; // path location
    private long duration; // Total Duration of the song
    private Image songImage; // This is for song title image that associated with the song
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Song() {
    }

    public Song(String songName, String artistName, String path,Image songImage) {
        this.songName = songName;
        this.artistName = artistName;
        this.songImage = songImage;
        this.path=path;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Image getSongImage() {
        return songImage;
    }

    public void setSongImage(Image songImage) {
        this.songImage = songImage;
    }
}
