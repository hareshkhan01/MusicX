package com.hitman.musicx.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hitman.musicx.R;
import com.hitman.musicx.model.Song;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SongListViewAdapter extends BaseAdapter {
    private final List<Song> songList;
    private final OnSongClickListener onSongClickListener;
    private final Context context;

    public SongListViewAdapter(List<Song> songList, OnSongClickListener onSongClickListener, Context context) {
        this.songList = songList;
        this.onSongClickListener = onSongClickListener;
        this.context = context;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return songList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.songs_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Song song = songList.get(position);
        holder.songName.setText(song.getSongName());
        holder.songArtist.setText(song.getArtistName());
        holder.songName.setSelected(true);

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(song.getPath());

        byte[] coverImageBytes = retriever.getEmbeddedPicture();
        if (coverImageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(coverImageBytes, 0, coverImageBytes.length);
            holder.songImageView.setImageBitmap(bitmap);
        }


        return convertView;
    }

    private static class ViewHolder {
        public TextView songName;
        public TextView songArtist;
        public ImageButton threeDotButton;
        public ImageView songImageView;

        public ViewHolder(View itemView) {
            songName = itemView.findViewById(R.id.song_row_title);
            songArtist = itemView.findViewById(R.id.song_row_artist_name);
            threeDotButton = itemView.findViewById(R.id.song_row_threeDot_button);
            songImageView = itemView.findViewById(R.id.song_row_imageview);
        }
    }

    public interface OnSongClickListener {
        void onSongClick(Song song);
    }
}

