package com.musicplayer.android

import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.musicplayer.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
  //  lateinit var videoList: ArrayList<Video>

    companion object {
        var sortOrder: Int = 0
        val sortingList = arrayOf(
            MediaStore.Audio.Media.DATE_ADDED + " DESC", MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.SIZE + " DESC", MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.title = "Music Player"
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

       // requestRuntimePermission()
        setNav()
     /*   if (requestRuntimePermission()) {
            videoList=getAllVideo()
            setNav()
        }*/
      //  setVp()
    }

    private fun setNav() {
        val bn = binding.bottomNav
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
       // setupWithNavController(bn, navController)
         binding.bottomNav.setupWithNavController(navController)
    }

    /*private fun setVp() {
        val vp = binding.vp
        val bn = binding.bottomNav
        val adapter = VpAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(VideoFragment())
        adapter.addFragment(MusicFragment())
        adapter.addFragment(DiscoverFragment())
        adapter.addFragment(MeFragment())
        vp.apply {
            this.adapter = adapter
         *//*
        }
        *//*bn.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.video->{
                    vp.currentItem=0
                }
                R.id.music->{
                    vp.currentItem=1
                }
                R.id.discover->{
                    vp.currentItem=2
                }
                R.id.me->{
                    vp.currentItem=3
                }
            }
        true
        }*//*

        *//*vp.registerOnPageChangeCallback(object:OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0->bn.menu.findItem(R.id.video).isChecked=true
                    1->bn.menu.findItem(R.id.music).isChecked=true
                    2->bn.menu.findItem(R.id.discover).isChecked=true
                    3->bn.menu.findItem(R.id.me).isChecked=true
                }
            }
        })*//*
    }*/

   /* private fun requestRuntimePermission(): Boolean{
        if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),13)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==13){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),13)
        }
    }*/
    override fun onResume() {
        super.onResume()
        //for sorting
        val sortEditor = getSharedPreferences("SORTING", MODE_PRIVATE)
        val sortValue = sortEditor.getInt("sortOrder", 0)
        /* if(sortOrder!= sortValue){
         }*/
    }

   /* @SuppressLint("Recycle", "Range")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllVideo(): ArrayList<Video>{
        val tempList = ArrayList<Video>()
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DURATION)
        val cursor = this.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,projection,null,null,
            MediaStore.Video.Media.DATE_ADDED + "DESC")
        if (cursor!=null)
            if (cursor.moveToNext())
                do{
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val folderC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val durationC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)).toLong()
                    try {
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val video = Video(title=titleC, id=idC, folderName = folderC,
                            duration = durationC, size=sizeC, path = pathC, artURi = artUriC
                        )
                        if (file.exists()) tempList.add(video)
                    }catch (_:java.lang.Exception){}
                }while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }
*/
}