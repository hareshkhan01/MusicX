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
    ContentResolver contentResolver ; // we will use contentResolver to query
    Context context;
//    public Repository(ContentResolver contentResolver){
//        this.contentResolver=contentResolver;
//    }
    public Repository(Context context){
        this.context=context;
        contentResolver=context.getContentResolver();
    }
    public ArrayList<Song> getSongList(){
        /*
        Each song file will be an object of Song class
        and there will be many music files so we store them in a array
         */
        ArrayList<Song> arrayList=new ArrayList<>();

        /*
        projection means in a query what columns we select from the data table
        and we should also remember that all files means each type of files are store in MediaStore Data base
        we use MediaStore data base and refer what type of file we want so here we want every type Music file

        Here we will use -> MediaStore.Audi.Media

         */
        String [] projection={
                MediaStore.Audio.Media.DATA,// -> this is for selecting the path of the file
                MediaStore.Audio.Media.TITLE,// -> this is for selecting the file name of the file
                MediaStore.Audio.Media.ARTIST,// -> this is for the file name author
                MediaStore.Audio.Media.DURATION// here is a music file so each music file has a duration so we it is for selecting the duration
        };


        /*
        This is for selecting only the music files from the storage
        0-> refers to the non music files (its not considers as a music file)
        1-> refers to the music files (it will consider as a music file)
        and both
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
        String selection = MediaStore.Audio.Media.IS_MUSIC+"==1";

        will give same result
         */
        String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";

        Cursor cursor=contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,selection,null,null);// we use contentResolver to make  a query and then the result is a type of Cursor that point the top means

        if(cursor!=null){ // Then we checking that cursor should not null and prevent from null pointer exception
            while (cursor.moveToNext()){// then we move the cursor point to the next cause first position of the cursor has no data and we will move it to the next until end of the row
                Song song=new Song(); // this will represent one music file
                /*
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA) -> this will give us the recipient column index and if it not present then it will throw an error
                cursor.getString(column_index) -> this give us the corresponding column value
                 */
                song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                song.setSongName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                song.setDuration(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                song.setArtistName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                arrayList.add(song); // add each music file
            }
        }
        assert cursor != null;
        cursor.close();// then we close the cursor prevent from data leak

        return arrayList;
    }
}
