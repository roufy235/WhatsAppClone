package com.example.roufy235.whatsappclone.Controllers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.widget.Toast
import com.example.roufy235.whatsappclone.R
import kotlinx.android.synthetic.main.activity_change_profile.*
import spencerstudios.com.bungeelib.Bungee

class ChangeProfileActivity : AppCompatActivity() {

    lateinit var toolbar : android.support.v7.widget.Toolbar
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)


        toolbar = findViewById(R.id.profileToolbar)

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        supportActionBar!!.title = "Profile"

        toolbar.setNavigationOnClickListener {
            finish()
            Bungee.fade(this)
        }
        roundedImage()
    }


    fun changeProfileNowClicked(view : View) {
        checkPermissionNow()
    }

    private fun checkPermissionNow() {


        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 12)
                return
            }
        }
        loadImageNow()
    }


    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<out String>, grantResults : IntArray) {
        when(requestCode) {
            12 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImageNow()
                } else {
                    Toast.makeText(this, "Unable to access your gallery", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        if (requestCode == 23 && data != null && resultCode == Activity.RESULT_OK) {
            val selectedImage = data.data

            val path = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = contentResolver.query(selectedImage, path, null,  null ,null)
            cursor.moveToFirst()

            val imagePath = cursor.getString(cursor.getColumnIndex(path[0]))

            val bitmap = BitmapFactory.decodeFile(imagePath)

            val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)

            rounded.isCircular = true

            changeProfileImageIV.setImageDrawable(rounded)

            cursor.close()

            uploadToFireBaseFireStorage()
        }
    }


    private fun uploadToFireBaseFireStorage() {
        uploadImageSpinner.visibility = View.VISIBLE
        changeProfileNow.isEnabled = false

        //Todo: profile image upload to firebase storage

    }

    private fun loadImageNow() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 23)
    }


    fun roundedImage() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.user_image)

        val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)

        rounded.isCircular = true
        changeProfileImageIV.setImageDrawable(rounded)
    }
}
