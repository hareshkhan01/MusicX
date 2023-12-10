package com.hitman.musicx.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hitman.musicx.ui.AlbumsFragment;
import com.hitman.musicx.ui.ArtistsFragment;
import com.hitman.musicx.ui.PlaylistsFragment;
import com.hitman.musicx.ui.SongsFragment;

/*
We use viewPager2 and for its adapter we extend FragmentStateAdapter
and inside constructor We take as args FragmentManager and LifeCycle
 */
public class ViewPagerMusicAdapter extends FragmentStateAdapter {


    public ViewPagerMusicAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    /*
    This override function createFragment returns the appropriate fragment for the selected tab
    [Because it counts from 0-3 for 4 layouts]
    We have total 4 tabs
    1-> Songs
    2-> Artists
    3-> Albums
    4->Playlists
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return SongsFragment.newInstance();
        } else if (position==1) {
            return ArtistsFragment.newInstance();
        }
        else if(position==2){
            return AlbumsFragment.newInstance();
        }else {
            return PlaylistsFragment.newInstance();
        }
    }


    // This override function gives the total number of tabs count
    @Override
    public int getItemCount() {
        return 4;
    }

}
