package com.musicplayer.android.video

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.musicplayer.android.R
import com.musicplayer.android.adapter.VideoSubAdapter
import com.musicplayer.android.databinding.FragmentVpVideoBinding
import com.musicplayer.android.model.VideoContentData
import com.musicplayer.android.model.VideoFolderData
import java.io.File

class VpVideoFragment : Fragment() {
    private lateinit var binding: FragmentVpVideoBinding
    private lateinit var mActivity: FragmentActivity

    companion object {
        var videoList: ArrayList<VideoContentData>? = null
        var folderList: ArrayList<VideoFolderData>? =null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            mActivity = context
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vp_video, container, false)
        binding = FragmentVpVideoBinding.bind(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requestRuntimePermission()) {
            videoList = getAllVideo()
            folderList = ArrayList()
            //Toast.makeText(mActivity, "testing", Toast.LENGTH_SHORT).show()
            binding.videoRecycler.layoutManager = GridLayoutManager(mActivity,2)
            binding.videoRecycler.adapter = VideoSubAdapter(mActivity, videoList!!)
        }
    }

    private fun isPermissionGranted():Boolean {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            val readExternalStorage: Int = ContextCompat.checkSelfPermission(mActivity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            return readExternalStorage == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun takePermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category,DEFAULT")
                intent.data = Uri.parse(String().format("package%s", requireContext().packageName))
                startActivityForResult(intent, 100)
            } catch (e: Exception) {
                val intent = Intent()
                intent.addCategory(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse(String().format("package%s", requireContext().packageName))
                startActivityForResult(intent, 100)
            }
        } else {
            ActivityCompat.requestPermissions(
                mActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101)
        }
    }
    private fun thumbnail(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent,102)
    }
     private fun pickFromGallery(view: View){
         if (isPermissionGranted()){
            thumbnail()
         }else
         {
             takePermissions()
         }
     }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==RESULT_OK){
            if (requestCode == 100){
                 if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()){
                        thumbnail()
                    }
                }
            }
        }
    }

    private fun requestRuntimePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 13)
            return false
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty()) {
            if (requestCode == 101) {
                val readExternalStorage: Boolean = grantResults[0]==PackageManager.PERMISSION_GRANTED
                if(readExternalStorage){
                    thumbnail()
                }
                else
                    takePermissions()
            }
        }
    }

    @SuppressLint("Range", "Recycle")
    private fun getAllVideo(): ArrayList<VideoContentData> {
        val tempList = ArrayList<VideoContentData>()
        val tempFolderList = ArrayList<String>()
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
                        //val pathC ="/storage/emulator/0/Download"
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val video = VideoContentData(
                            date = dateC,
                            title = titleC,
                            id = idC,
                            folderName = folderC,
                            duration = 0,
                            size = sizeC,
                            path = pathC,
                            artURi = artUriC,
                        )
                        if (file.exists()) tempList.add(video)
                        if (!tempFolderList.contains(folderC)){
                            tempFolderList.add(folderC)
                            folderList!!.add(VideoFolderData(id = folderIdC, folderName = folderC))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext())
        cursor!!.close()
        return tempList
    }
}

