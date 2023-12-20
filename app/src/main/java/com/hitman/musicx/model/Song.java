package com.hitman.musicx.model;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

public class Song {
    private String songName; // Song title or the name
    private String artistName; // Artist Name
    private String path; // path location
    private int duration; // Total Duration of the song
    private String artWork; // This is for song album image that associated with the song
    private long albumID;

    private byte[] songCoverImage;

    public byte[] getSongCoverImage() {
        return songCoverImage;
    }

    public void setSongCoverImage(byte[] songCoverImage) {
        this.songCoverImage = songCoverImage;
    }

    public long getAlbumID() {
        return albumID;
    }

    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Song() {
    }

    public Song(String songName, String artistName, String path, String artWork,byte[] songCoverImage) {
        this.songName = songName;
        this.artistName = artistName;
        this.artWork=artWork;
        this.path=path;
        this.songCoverImage=songCoverImage;
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

    public String getArtWork() {
        return artWork;
    }

    public void setArtWork(String artWork) {
        this.artWork = artWork;
    }
}
