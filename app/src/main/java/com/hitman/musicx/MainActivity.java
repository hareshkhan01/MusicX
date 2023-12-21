package com.hitman.musicx;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hitman.musicx.adapter.SongRecyclerViewAdapter;
import com.hitman.musicx.adapter.ViewPagerMusicAdapter;
import com.hitman.musicx.data.Repository;
import com.hitman.musicx.model.SharedViewModel;
import com.hitman.musicx.model.Song;
import com.hitman.musicx.model.SongViewModel;
import com.hitman.musicx.player.MusicPlayer;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CODE=9999;
    private ViewPager2 viewPager2;
    private TabLayout tab;
    private ViewPagerMusicAdapter viewPagerMusicAdapter;
    private SeekBar seekBar;
    private ImageButton nextButton;
    private ImageButton floatingBarPlayPauseButton;
    private ImageView floatBarSongImage;
    private CardView floatingCardView;
    private TextView floatingBarSongTitle;
    private TextView floatBarSongArtistName;
    private SharedViewModel sharedViewModel;
    private LinearLayout floatingBarLayout;
    private CircleImageView bottomSheetCircularImageView;
    private CircularSeekBar bottomSheetSeekBar;
    private TextView musicCurrentStateTime;
    private TextView musicEndTime;
    private TextView bottomSongTitleName;
    private  TextView bottomArtistName;
    private TextView musicModeText;
    private ImageButton shuffleButton;
    private ImageButton musicBackWardButton;
    private ImageButton musicForwardButton;
    private ImageButton bottomSheetPlayPauseButton;
    private ImageButton loopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermission();
        }
        sharedViewModel=new ViewModelProvider(this).get(SharedViewModel.class);
        SongViewModel songViewModel=new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(SongViewModel.class);

        MusicPlayer musicPlayer=new MusicPlayer();


        //Floating bar view set up and initialization
        seekBar=findViewById(R.id.seekBar);
        seekBar.setEnabled(false);
        floatingBarLayout=findViewById(R.id.floatingBar);
        floatingBarLayout.setVisibility(View.GONE);
        sharedViewModel.setIsMusicPlaying(false);
        floatBarSongImage=findViewById(R.id.floating_bar_song_imageview);
        floatingBarPlayPauseButton=findViewById(R.id.floating_bar_play_pause_button);
        nextButton=findViewById(R.id.floating_bar_next_buttton);
        floatingBarSongTitle=findViewById(R.id.floating_bar_song_title);
        floatBarSongArtistName=findViewById(R.id.floating_bar_song_artist);
        floatingCardView=findViewById(R.id.floating_bar_card_view);
        floatingBarSongTitle.setSelected(true);
        nextButton.setOnClickListener(v->{
            int position=Objects.requireNonNull(sharedViewModel.getCurrentSongPosition().getValue());
            List<Song> songList=sharedViewModel.getSongsList().getValue();
            assert songList != null;
            if(position+1<songList.size()){
                sharedViewModel.setCurrentSongPosition(position+1);
                sharedViewModel.setCurrentSong(songList.get(position+1));
            }
        });
        // set on click for bottom sheet trigger
        floatingCardView.setOnClickListener(view->{
            showMusicBottomSheet();
            setupBottomSheet();
            updateCircularSeekBar();
        });
        sharedViewModel.getIsMusicPlaying().observe(MainActivity.this, aBoolean -> {
            if(aBoolean){
                Log.d("floatBarView", "onCreate: Song is playing Float bar view");
                floatingBarLayout.setVisibility(View.VISIBLE);
            }
            else{
                Log.d("floatBarView", "onCreate: Song is not playing Float bar view");
                floatingBarLayout.setVisibility(View.GONE);
            }
        });
        sharedViewModel.getCurrentSong().observe(this, song -> {
            if(song!=null){
                if(!MusicPlayer.isMediaPlayerNull()){
                    if(MusicPlayer.isAnyMusicPlaying()){
                        MusicPlayer.pauseCurrentMusic();
                    }
                    MusicPlayer.playMusic(song);
                    setupFloatingBarView(song);
                }
            }
        });

        floatingBarPlayPauseButton.setOnClickListener(v -> {
            if(!MusicPlayer.isMediaPlayerNull()){
                if(MusicPlayer.isAnyMusicPlaying()){
                    MusicPlayer.pauseCurrentMusic();
                    floatingBarPlayPauseButton.setImageResource(R.drawable.ic_float_bar_play_icon_dark);
                }
                else{
                    MusicPlayer.startTheMusic();
                    floatingBarPlayPauseButton.setImageResource(R.drawable.ic_float_bar_pause_icon_dark);
                }
            }
        });

        // End Floating Bar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MusicX");
        setSupportActionBar(toolbar);

//        ====This code is for removing the toolbar title====

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false); // Hide the title
        }

        // Set up the TabLayout
        tab=findViewById(R.id.tab_layout);
        viewPager2=findViewById(R.id.view_pager);

        System.out.println("This is fucked up example");
        System.out.println("This is the 2nd Fucked up message");

        // Set the viewPager adapter
        viewPagerMusicAdapter=new ViewPagerMusicAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(viewPagerMusicAdapter);

        /*
        TabLayoutMediator is used to sync viewPager with the tabLayout
        and also used to set the tab titles.
         */
        new TabLayoutMediator(tab, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Songs");
                    break;
                case 1:
                    tab.setText("Artist");
                    break;
                case 2:
                    tab.setText("Albums");
                    break;
                case 3:
                    tab.setText("Playlists");
                    break;
            }
        }).attach();// This attach method will sync the tabLayout and viewPager
        tab.setTabTextColors(Color.BLACK,Color.BLUE);

    }

    private void setupBottomSheet() {
        Song song=sharedViewModel.getCurrentSong().getValue();
        assert song != null;
        Glide.with(this).asBitmap()
                .load(Objects.requireNonNull(song.getSongCoverImage()))
                .into(bottomSheetCircularImageView);
        bottomSongTitleName.setText(song.getSongName().trim());
        bottomArtistName.setText(song.getArtistName().trim());
        bottomSheetSeekBar.setMax((float)song.getDuration());
        bottomSheetSeekBar.setProgress(MusicPlayer.getMusicCurrentPosition());
        if(MusicPlayer.isAnyMusicPlaying()){
            bottomSheetPlayPauseButton.setImageResource(R.drawable.ic_pause_icon);
        }else{
            bottomSheetPlayPauseButton.setImageResource(R.drawable.ic_play_button_icon_bottomsheet_dark);
        }
        int totalSecond=song.getDuration()/1000;
        int minutes=totalSecond/60;
        int second=totalSecond%60;
        musicEndTime.setText(String.format("%02d:%02d",minutes,second));
        bottomSheetPlayPauseButton.setOnClickListener(v->{
            if(MusicPlayer.isAnyMusicPlaying()){
                MusicPlayer.pauseCurrentMusic();
                bottomSheetPlayPauseButton.setImageResource(R.drawable.ic_play_button_icon_bottomsheet_dark);
            }
            else {
                MusicPlayer.startTheMusic();
                bottomSheetPlayPauseButton.setImageResource(R.drawable.ic_pause_icon);
            }
        });

    }

    private void setupFloatingBarView(Song song) {
        floatingBarSongTitle.setText(song.getSongName().trim());
        floatBarSongArtistName.setText(song.getArtistName());
        floatingBarPlayPauseButton.setImageResource(R.drawable.ic_float_bar_pause_icon_dark);
        seekBar.setProgress(0);
        seekBar.setMax(song.getDuration());
        Glide.with(MainActivity.this).asBitmap()
                        .load(song.getSongCoverImage())
                                .into(floatBarSongImage);
        updateFloatingBarSeekBar();
    }

    private void initializeBottomSheetView(View itemView) {
        bottomSheetCircularImageView=itemView.findViewById(R.id.music_imageview_bottom_sheet);
        bottomSheetSeekBar=itemView.findViewById(R.id.circularSeekBar);
        bottomSheetPlayPauseButton=itemView.findViewById(R.id.bottomSheetPlayPauseButton);
        musicBackWardButton=itemView.findViewById(R.id.bottomSheetBackwardButton);
        musicForwardButton=itemView.findViewById(R.id.bottomSheetForwardButton);
        shuffleButton=itemView.findViewById(R.id.shuffle_button);
        loopButton=itemView.findViewById(R.id.loopButton);
        musicEndTime=itemView.findViewById(R.id.music_end_time_bottomsheet);
        musicCurrentStateTime=itemView.findViewById(R.id.music_current_time_state_bottomsheet);
        bottomSongTitleName=itemView.findViewById(R.id.bottomSheetMusicTitle);
        bottomArtistName=itemView.findViewById(R.id.bottomSheetArtistTextView);
        musicModeText=itemView.findViewById(R.id.muic_mode_textview_bottomsheet);
    }

    private void updateFloatingBarSeekBar() {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if(!MusicPlayer.isMediaPlayerNull()){
                seekBar.setProgress(MusicPlayer.getMusicCurrentPosition());
                updateFloatingBarSeekBar();
            }
        },0);
    }
    private void updateCircularSeekBar() {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if(!MusicPlayer.isMediaPlayerNull()){
                int totalMillis=MusicPlayer.getMusicCurrentPosition();
                int totalSecond=totalMillis/1000;
                int minutes=totalSecond/60;
                int second=totalSecond%60;
                String formattedTime= String.format("%02d:%02d",minutes,second);
                musicCurrentStateTime.setText(formattedTime);
                bottomSheetSeekBar.setProgress(totalMillis);

                updateCircularSeekBar();
            }
        },0);
    }


    // Method to display the music bottom sheet
    private void showMusicBottomSheet() {
        // Create a BottomSheetDialog instance
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);

        // Declare BottomSheetBehavior and initialize it
        BottomSheetBehavior<View> bottomSheetBehavior;

        // Inflate the layout for the bottom sheet
        View bottomSheetView = LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_music_bottom_sheet, null);

        // Set the bottom sheet content view
        bottomSheetDialog.setContentView(bottomSheetView);

        // Get the BottomSheetBehavior from the parent view of the bottom sheet
        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());

        // Set the state of the bottom sheet to expanded
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        // Set the minimum height of the bottom sheet to the screen height
        CoordinatorLayout layout = bottomSheetDialog.findViewById(R.id.bottom_sheet_root_layout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        // Set up click listener for the angle down button to hide the bottom sheet
        ImageButton angleDown = bottomSheetView.findViewById(R.id.angle_down);
        angleDown.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
        initializeBottomSheetView(bottomSheetView);
        // Show the bottom sheet
        bottomSheetDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setIconAsPerTheme();
    }


    public void setIconAsPerTheme() {

        int currentUiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(currentUiMode==Configuration.UI_MODE_NIGHT_YES){
            floatingBarPlayPauseButton.setImageResource(R.drawable.ic_float_bar_play_icon_light);
            nextButton.setImageResource(R.drawable.ic_float_bar_next_icon_light);
        }
        else {
            floatingBarPlayPauseButton.setImageResource(R.drawable.ic_float_bar_play_icon_dark);
            nextButton.setImageResource(R.drawable.ic_float_bar_next_icon_dark);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED){
            displaySongList();
            Log.d("myPerm", "checkPermission: already permited");
        }
        else {
            Log.d("myPerm", "checkPermission: not have permission");
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_MEDIA_AUDIO},PERMISSION_REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQUEST_CODE){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_AUDIO)
                        ==PackageManager.PERMISSION_GRANTED){
                    displaySongList();
                }
                else {
                    if(shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO)){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("This permission is mandatory to run the application")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    checkPermission();
                                })
                                .setNegativeButton("Cancel",(dialog,which)->{
                                    Toast.makeText(this,"Please check the permission manually",Toast.LENGTH_SHORT).show();
                                })
                                .show();
                    }
                }
            }
        }


    private void displaySongList() {
        Log.d("myPerm", "displaySongList: Permission Granted and now ready to show song list");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MusicPlayer.isAnyMusicPlaying()){
            MusicPlayer.pauseCurrentMusic();
         }
        MusicPlayer.releaseMediaPlayer();
        MusicPlayer.stopCurrentMusic();
        MusicPlayer.setMediaPlayerNull();
    }
}