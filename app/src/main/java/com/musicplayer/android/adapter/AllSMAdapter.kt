package com.musicplayer.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.musicplayer.android.R
import com.musicplayer.android.model.AudioData

class AllSMAdapter(val context: Context, val mList:List<AudioData>): RecyclerView.Adapter<AllSMAdapter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_audio_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val list=mList[position]
        holder.noOFSong.text= list.number.toString()
        holder.audioName.text=list.audioName
        holder.audioDes.text=list.audioDescription
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class viewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val noOFSong : TextView =itemView.findViewById(R.id.tvOrder)
        val audioName : TextView =itemView.findViewById(R.id.tvSongName)
        val audioDes : TextView =itemView.findViewById(R.id.tvArtistAndAlbum)
    }
}