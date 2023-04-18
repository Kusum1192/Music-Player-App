package com.musicplayer.android.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.musicplayer.android.R
import com.musicplayer.android.adapter.VplistAdapter
import com.musicplayer.android.databinding.FragmentPlaylistVideoBinding
import com.musicplayer.android.model.VplistData

class PlaylistVideoFragment : Fragment() {
    lateinit var binding:FragmentPlaylistVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_playlist_video, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = ArrayList<VplistData>()
        for (i in 1..20) {
            data.add(VplistData(R.drawable.youtube_cover, "Video Name","Item $i"))
        }
        val adapter = context?.let { VplistAdapter(it,data) }
        binding.videoPlaylistRecyclerFolder.adapter = adapter
    }
}