package com.hitman.musicx.player;

import android.app.Application;
import android.media.MediaPlayer;
import android.util.Log;

import com.hitman.musicx.model.Song;

import java.io.IOException;

public class MusicPlayer {
    private static MediaPlayer mediaPlayer;
    public static void playMusic(Song song){
        mediaPlayer=new MediaPlayer(); // we just initialize the mediaPlayer simply because we will play music from storage or API web link

        try {
               mediaPlayer.setDataSource(song.getPath()); // set the path where the music file is present

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
        create a separate OnPreparedListener
         */
        MediaPlayer.OnPreparedListener onPreparedListener=new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start(); // when the mediaPlayer is ready we play the music
            }
        };
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mp!=null){
                    mp.start();
                }
            }
        });
        mediaPlayer.setOnPreparedListener(onPreparedListener); // set a preparesListener means set a listener that will execute the respected code inside that when the mediaPlayer will ready after async request
        mediaPlayer.prepareAsync(); // making an asynchronous request

    }

    /*
    and here we just make the simple functions for our benefits
    or
    we can also say we just override the normal  function of MediaPlayer class for out benefits
     */
    public static void stopCurrentMusic()
    {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
    public static void pauseCurrentMusic()
    {
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
    public static boolean isAnyMusicPlaying()
    {
        if(mediaPlayer!=null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }
    public static void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
    public static void setMediaPlayerNull()
    {
        if(mediaPlayer!=null){
            mediaPlayer=null;
        }
    }
    public  static boolean isMediaPlayerNull(){
        return mediaPlayer == null;
    }

}
