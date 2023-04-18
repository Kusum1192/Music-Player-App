package com.musicplayer.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.musicplayer.android.video.FolderVideoFragment
import com.musicplayer.android.video.PlaylistVideoFragment
import com.musicplayer.android.video.VpVideoFragment

class VpVideoAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                VpVideoFragment()
            }
            1->{
                FolderVideoFragment()
            }
            2->{
                PlaylistVideoFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}