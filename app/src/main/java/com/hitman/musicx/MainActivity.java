package com.hitman.musicx;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hitman.musicx.adapter.ViewPagerMusicAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tab;
    private ViewPagerMusicAdapter viewPagerMusicAdapter;
    private SeekBar seekBar;
    private ImageButton nextButton;
    private ImageButton playPauseButton;
    private CardView floatingCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=findViewById(R.id.seekBar);
        seekBar.setEnabled(false);

        //Floating bar view set up and initialization
        playPauseButton=findViewById(R.id.floating_bar_play_pause_button);
        nextButton=findViewById(R.id.floating_bar_next_buttton);

        floatingCardView=findViewById(R.id.floating_bar_card_view);
        floatingCardView.setOnClickListener(view->{
            showMusicBottomSheet();
        });

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


}