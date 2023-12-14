package com.hitman.musicx.model;

import android.media.Image;
import android.net.Uri;

public class Song {
    private String songName;
    private String artistName;
    private Uri songURL;
    private Image songImage;

    public Song() {
    }

    public Song(String songName, String artistName, Uri songURL, Image songImage) {
        this.songName = songName;
        this.artistName = artistName;
        this.songURL = songURL;
        this.songImage = songImage;
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

    public Uri getSongURL() {
        return songURL;
    }

    public void setSongURL(Uri songURL) {
        this.songURL = songURL;
    }

    public Image getSongImage() {
        return songImage;
    }

    public void setSongImage(Image songImage) {
        this.songImage = songImage;
    }
}
