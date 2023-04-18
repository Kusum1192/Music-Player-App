package com.musicplayer.android.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.musicplayer.android.R
import com.musicplayer.android.adapter.MusicAdapter
import com.musicplayer.android.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {
    private lateinit var binding: FragmentMusicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vpa = MusicAdapter(childFragmentManager,lifecycle)
        binding.vpMusic.adapter=vpa
        TabLayoutMediator(binding.tabLayoutMusic,binding.vpMusic){tab,position->
            when(position){
                0->{
                    tab.text="All Songs"
                }
                1->{
                    tab.text="Playlist"
                }
                2->{
                    tab.text="Folder"
                }
                3->{
                    tab.text="Album"
                }
                4->{
                    tab.text="Artist"
                }
            }
        }.attach()
        }
}