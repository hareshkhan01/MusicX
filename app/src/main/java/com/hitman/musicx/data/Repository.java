package com.hitman.musicx.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    ContentResolver contentResolver ;
    public Repository(ContentResolver contentResolver){
        this.contentResolver=contentResolver;
    }
}
