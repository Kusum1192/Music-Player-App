package com.musicplayer.android.video

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.musicplayer.android.R
import com.musicplayer.android.adapter.VideoFolderAdapter
import com.musicplayer.android.databinding.FragmentFolderVideoBinding
import com.musicplayer.android.model.VideoFolderData

class FolderVideoFragment : Fragment() {
    lateinit var binding: FragmentFolderVideoBinding
    private lateinit var mActivity: FragmentActivity

    companion object{
        var folderList: ArrayList<VideoFolderData>? =null

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_folder_video, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      /*  val folderList =ArrayList<VideoFolderData>()
        folderList.add(VideoFolderData("1","videos"))
        folderList.add(VideoFolderData("2","videos"))
        folderList.add(VideoFolderData("3","videos"))*/
        folderList = getAllVideo()
        binding.videoRecyclerFolder.layoutManager = GridLayoutManager(mActivity,3)
        binding.videoRecyclerFolder.adapter = VideoFolderAdapter(mActivity, folderList!!)

    }
    @SuppressLint("Range", "Recycle")
    private fun getAllVideo(): ArrayList<VideoFolderData> {
        val tempFolderList = ArrayList<VideoFolderData>()
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.BUCKET_ID
        )

        val cursor = mActivity.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        //sort Order=MediaStore.Video.Media.DATE_TAKEN + "DESC"
        Log.d("TAG", "getAllVideo: $cursor")
        if (cursor != null)
            if (cursor.moveToNext())
                do {
                    val dateC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED))
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val folderIdC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
//                  val durationC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)).toLong()
                    try {
                        if (tempFolderList.add(VideoFolderData(id = folderIdC, folderName = folderC))){

                        }
                       // if (folderList!!.add(VideoFolderData(id = folderIdC, folderName = folderC))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } while (cursor.moveToNext())
        cursor!!.close()
        return tempFolderList
    }

}