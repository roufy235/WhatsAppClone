package com.example.roufy235.whatsappclone.Controllers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.widget.Toast
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Services.Data
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_change_profile.*
import spencerstudios.com.bungeelib.Bungee
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ChangeProfileActivity : AppCompatActivity() {

    lateinit var toolbar : android.support.v7.widget.Toolbar

    private lateinit var  mFirebaseAnalytics : FirebaseAnalytics
    private lateinit var mRef : DatabaseReference
    private lateinit var database : FirebaseDatabase

    var path : String? = null

    lateinit var mStorageRef : StorageReference
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile)

        database = FirebaseDatabase.getInstance()
        mRef = database.reference
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)






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

            //val bitmap = BitmapFactory.decodeFile(imagePath)

            //val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)

           // rounded.isCircular = true

            changeProfileImageIV.setImageBitmap(BitmapFactory.decodeFile(imagePath))

            cursor.close()

            uploadToFireBaseFireStorage()
        }
    }


    private fun uploadToFireBaseFireStorage() {

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://whatsappclone-e9e65.appspot.com")

        uploadImageSpinner.visibility = View.VISIBLE
        changeProfileNow.isEnabled = false

        //unique name
        val df = SimpleDateFormat("dd-mm-YYYY HH:mm:ss")
        val dateObj = Date()
        val imageName = df.format(dateObj) + ".jpg"

        val imageRef = mStorageRef.child("profileImage/$imageName")


        //get image bitmap
        changeProfileImageIV.isDrawingCacheEnabled = true
        changeProfileImageIV.buildDrawingCache()

        val drawable = changeProfileImageIV.drawable as BitmapDrawable
        val bit = drawable.bitmap

        val baos = ByteArrayOutputStream()

        bit.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()


        val uploadTask = imageRef.putBytes(data)


        uploadTask.addOnFailureListener { failed ->

            // Toast.makeText(this, failed.message.toString() , Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener { taskSnapshot ->

            val downloadUrl = taskSnapshot.storage.downloadUrl

            mRef.child("contacts").child("profileImage").child(Data.getPhoneNumber(this)).setValue(downloadUrl.toString())

            uploadImageSpinner.visibility = View.INVISIBLE
            changeProfileNow.isEnabled = true
            Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()

        }

    }

    private fun loadImageNow() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 23)
    }


    fun roundedImage() {
       // val bitmap = BitmapFactory.decodeResource(resources, R.drawable.user_image)

       // val rounded = RoundedBitmapDrawableFactory.create(resources, bitmap)

        //rounded.isCircular = true
        //changeProfileImageIV.setImageDrawable(rounded)



        mRef.child("contacts").child("profileImage").child(Data.getPhoneNumber(this)).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0 : DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0 : DataSnapshot) {


                if (p0.value != null) {
                    path = p0.value as String

                    Picasso
                            .get()
                            .load(path)
                            .transform(CropCircleTransformation())
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .into(changeProfileImageIV)
                } else {
                    changeProfileImageIV.setImageResource(R.drawable.ic_person_black_24dp)
                }
            }

        })

    }
}
