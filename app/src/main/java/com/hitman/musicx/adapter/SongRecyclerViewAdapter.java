package com.hitman.musicx.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
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

import com.hitman.musicx.R;
import com.hitman.musicx.model.Song;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

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
//        Picasso.get().load(song.getArtWork())
//                .placeholder(R.drawable.ic_music_placeholder_icon_dark)
//                .error(R.drawable.ic_music_placeholder_icon_dark)
//                .into(holder.songImageView);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(song.getPath());

        byte[] coverImageBytes = retriever.getEmbeddedPicture();
        if(coverImageBytes!=null) {
            Bitmap bitmap=BitmapFactory.decodeByteArray(coverImageBytes, 0, coverImageBytes.length);
            holder.songImageView.setImageBitmap(bitmap);
        }



    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
