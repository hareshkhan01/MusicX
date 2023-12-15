package com.hitman.musicx.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;

import com.hitman.musicx.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    ContentResolver contentResolver ;
    Context context;
    public Repository(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }
    public ArrayList<Song> getSongList(){
        ArrayList<Song> arrayList=new ArrayList<>();
        String [] projection={
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";

        Cursor cursor=contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,selection,null,null);

        if(cursor!=null){
            while (cursor.moveToNext()){
                Song song=new Song();
                song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                song.setSongName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                song.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                arrayList.add(song);
            }
        }
        for (Song song: arrayList){
            Log.d("checkingSongs", "SONG_NAME: "+song.getSongName());
            Log.d("checkingSongs", "SONG_DURATION: "+song.getDuration());
            Log.d("checkingSongs", "SONG_PATH: "+song.getPath());
        }

        return arrayList;
    }
}
