package com.hitman.musicx.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hitman.musicx.R;
import com.hitman.musicx.model.Song;

import java.util.List;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongRecyclerViewAdapter.ViewHolder> {
    private final List<Song> songList;
    private OnSongClickListener onSongClickListener;
    public SongRecyclerViewAdapter(List<Song> songList,OnSongClickListener onSongClickListener){
        this.songList=songList;
        this.onSongClickListener=onSongClickListener;
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
        Log.d("RAdapter", "Name: "+holder.songName.getText().toString());
        Log.d("RAdapter", "Artist: "+holder.songArtist.getText().toString());
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView songName;
        public TextView songArtist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.song_row_title);
            songArtist=itemView.findViewById(R.id.song_row_artist_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id=v.getId();
            if(id!=R.id.song_row_threeDot_button){
                Log.d("SongClick", "onClick: Song Clicked "+getAdapterPosition());
                onSongClickListener.onSongClick(songList.get(getAdapterPosition()));
            }
        }
    }

    public interface OnSongClickListener{
        void onSongClick(Song song);
    }
}
