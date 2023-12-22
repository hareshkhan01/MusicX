package com.hitman.musicx.player;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import com.hitman.musicx.model.Song;

import java.io.IOException;



public class MusicPlayer extends Application{
    private MediaPlayer mediaPlayer;
    private static MusicPlayer INSTANCE;
    private MusicPlayer(){
        mediaPlayer=new MediaPlayer();
    }
    public static MusicPlayer getInstance(){
        if(INSTANCE==null){
            synchronized (MusicPlayer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MusicPlayer();
                }
            }
        }
        return INSTANCE;
    }
    public  void playMusic (Song song){
        try {
            if(mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.reset(); // Reset the MediaPlayer to the Idle state
                mediaPlayer.setDataSource(song.getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException | IllegalStateException e) {
            throw new RuntimeException(e);
        }

        /*
        create a separate OnPreparedListener
         */

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mp!=null){
                    mp.start();
                }
            }
        });


    }

    /*
    and here we just make the simple functions for our benefits
    or
    we can also say we just override the normal  function of MediaPlayer class for out benefits
     */
    public void startTheMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
        }
    }
    public void stopCurrentMusic()
    {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }
    }
    public void pauseCurrentMusic()
    {
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
    public boolean isAnyMusicPlaying()
    {
        if(mediaPlayer!=null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }
    public void seekTo(int time){
        if (mediaPlayer!=null){
            mediaPlayer.seekTo(time);
        }
    }
    public int getMusicCurrentPosition(){
        return mediaPlayer!=null?mediaPlayer.getCurrentPosition():-1;
    }
    public void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
    public void setMediaPlayerNull()
    {
        if(mediaPlayer!=null){
            mediaPlayer=null;
        }
    }
    public boolean isMediaPlayerNull(){
        return mediaPlayer == null;
    }
}
