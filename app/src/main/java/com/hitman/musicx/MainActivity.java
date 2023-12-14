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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hitman.musicx.adapter.ViewPagerMusicAdapter;
import com.hitman.musicx.data.Repository;

import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CODE=9999;
    private ViewPager2 viewPager2;
    private TabLayout tab;
    private ViewPagerMusicAdapter viewPagerMusicAdapter;
    private SeekBar seekBar;
    private ImageButton nextButton;
    private ImageButton playPauseButton;
    private CardView floatingCardView;
    private TextView floatingBarSongTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermission();
        }
        Repository repository=new Repository();
        List<File> songList=repository.getSongsList(new File(Environment.getExternalStorageState()));
        Log.d("songList", "onCreate: "+songList.toString());

        seekBar=findViewById(R.id.seekBar);
        seekBar.setEnabled(false);

        //Floating bar view set up and initialization
        playPauseButton=findViewById(R.id.floating_bar_play_pause_button);
        nextButton=findViewById(R.id.floating_bar_next_buttton);
        floatingBarSongTitle=findViewById(R.id.floating_bar_song_title);
        floatingCardView=findViewById(R.id.floating_bar_card_view);

        floatingBarSongTitle.setSelected(true);
        floatingCardView.setOnClickListener(view->{
            showMusicBottomSheet();
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


        seekBar.setProgress(30);
    }

    private void showMusicBottomSheet() {
        BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(MainActivity.this);
        BottomSheetBehavior<View> bottomSheetBehavior;
        View bottomSheetView= LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_music_bottom_sheet,null);
        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetBehavior=BottomSheetBehavior.from((View) bottomSheetView.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CoordinatorLayout layout= bottomSheetDialog.findViewById(R.id.bottom_sheet_root_layout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        ImageButton angleDown=(ImageButton) bottomSheetView.findViewById(R.id.angle_down);
        angleDown.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });

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
        setIconAsPerTheme();
    }


    public void setIconAsPerTheme() {

        int currentUiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(currentUiMode==Configuration.UI_MODE_NIGHT_YES){
            playPauseButton.setImageResource(R.drawable.ic_float_bar_play_icon_light);
            nextButton.setImageResource(R.drawable.ic_float_bar_next_icon_light);
        }
        else {
            playPauseButton.setImageResource(R.drawable.ic_float_bar_play_icon_dark);
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
}