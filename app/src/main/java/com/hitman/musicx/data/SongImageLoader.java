package com.hitman.musicx.data;

import android.media.MediaMetadataRetriever;

import com.hitman.musicx.model.Song;

import java.util.ArrayList;

public class SongImageLoader {
    public static ArrayList<Song> setSongsImage(ArrayList<Song> songArrayList){
        for (Song song:songArrayList){
            MediaMetadataRetriever mmr=new MediaMetadataRetriever();
            mmr.setDataSource(song.getPath());
            byte[] coverImage=mmr.getEmbeddedPicture();
            song.setSongCoverImage(coverImage);
        }
        return songArrayList;
    }
    public static Song setSongImage(Song song){
        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
        mmr.setDataSource(song.getPath());
        byte[] coverImage= mmr.getEmbeddedPicture();
        song.setSongCoverImage(coverImage);
        return song;
    }
}
