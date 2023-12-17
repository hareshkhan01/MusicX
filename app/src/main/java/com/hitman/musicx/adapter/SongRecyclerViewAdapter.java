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
                    .into(holder.songImageView);
        }

        // This is approach is good not ui lag but still some little bit of lag remains but the ui image loading is so annoying it changes again an aging while scrolling
//    LoadImageTask loadImageTask=new LoadImageTask(holder.songImageView,song.getPath());
//    loadImageTask.execute(song.getPath());



        // This method is still laggy a bit but itna too chalta hain bar bar image loading hone se acha hai
//          ExecutorService executor =Executors.newSingleThreadExecutor();
//          executor.execute(()->{
//              MediaMetadataRetriever mmr=new MediaMetadataRetriever();
//              mmr.setDataSource(song.getPath());
//              byte[] coverImage=mmr.getEmbeddedPicture();
//              if(coverImage!=null){
//                  new Handler(Looper.getMainLooper()).post(()->{
//                      Glide.with(context).asBitmap()
//                              .load(coverImage)
//                              .into(holder.songImageView);
//                  });
//              }
//
//          });

//        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
//        mmr.setDataSource(song.getPath());
//        byte[] coverImage=mmr.getEmbeddedPicture();
//        if(coverImage!=null){
//                Glide.with(context).asBitmap()
//                        .load(coverImage)
//                        .into(holder.songImageView);
//        }


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


//    // AsyncTask to load album cover image in the background
    public static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;
        private final String songPath;
        private static final Map<String, Bitmap> imageCache = new HashMap<>();

        public LoadImageTask(ImageView imageView, String songPath) {
            imageViewReference = new WeakReference<>(imageView);
            this.songPath = songPath;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap cachedBitmap = imageCache.get(songPath);
            if (cachedBitmap != null) {
                return cachedBitmap;
            }

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(songPath);

            byte[] coverImageBytes = retriever.getEmbeddedPicture();

            if (coverImageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(coverImageBytes, 0, coverImageBytes.length);
                imageCache.put(songPath, bitmap);
                return bitmap;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null && songPath.equals(imageView.getTag())) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }




}
