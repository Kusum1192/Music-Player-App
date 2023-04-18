package com.musicplayer.android.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.musicplayer.android.R
import com.musicplayer.android.adapter.AllSMAdapter
import com.musicplayer.android.databinding.FragmentAllSongMusicBinding
import com.musicplayer.android.model.AudioData


class AllSongMusicFragment : Fragment() {
    lateinit var binding : FragmentAllSongMusicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_all_song_music, container, false)
        binding= FragmentAllSongMusicBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = ArrayList<AudioData>()

        for (i in 1..20) {
            data.add(AudioData(1,"Item $i","Video Detail"))
        }
        val adapter = context?.let { AllSMAdapter(it,data) }
        binding.audioRecycler.adapter = adapter
    }

}