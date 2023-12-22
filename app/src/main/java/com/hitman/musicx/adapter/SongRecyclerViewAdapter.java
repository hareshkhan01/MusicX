package com.hitman.musicx.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hitman.musicx.R;
import com.hitman.musicx.model.Song;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {
    private final List<Song> songList;
    private final OnSongClickListener onSongClickListener;
    private final Context context;
    public SongRecyclerViewAdapter(List<Song> songList,OnSongClickListener onSongClickListener,Context context){
        this.songList=songList;
        this.onSongClickListener=onSongClickListener;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("OnBindViewHolder", "onBindViewHolder: Run");
        Song song=songList.get(position);
        holder.songName.setText(song.getSongName());
        holder.songArtist.setText(song.getArtistName());
        holder.songName.setSelected(true);

        if(song.getSongCoverImage()!=null){
            Glide.with(context).asBitmap()
                    .load(song.getSongCoverImage())
                    .placeholder(R.drawable.ic_music_placeholder_icon_dark)
                    .error(R.drawable.ic_music_placeholder_icon_dark)
                    .into(holder.songImageView);
        }
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView songName;
        public TextView songArtist;
        public ImageButton threeDotButton;
        public ImageView songImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.song_row_title);
            songArtist=itemView.findViewById(R.id.song_row_artist_name);
            threeDotButton=itemView.findViewById(R.id.song_row_threeDot_button);
            songImageView=itemView.findViewById(R.id.song_row_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id=v.getId();
            int position=getAdapterPosition();
            if(id!=R.id.song_row_threeDot_button){
                onSongClickListener.onSongClick(songList.get(position),position);
            }
        }
    }

    public interface OnSongClickListener{
        void onSongClick(Song song,int position);
    }


}
