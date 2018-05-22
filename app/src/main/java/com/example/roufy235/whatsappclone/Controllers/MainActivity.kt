package com.example.roufy235.whatsappclone.Controllers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.roufy235.whatsappclone.Adapters.ChatRecyclerAdapter
import com.example.roufy235.whatsappclone.Fragments.Calls
import com.example.roufy235.whatsappclone.Fragments.Chat
import com.example.roufy235.whatsappclone.Fragments.Status
import com.example.roufy235.whatsappclone.Model.ChatsModel
import com.example.roufy235.whatsappclone.Model.FirebaseDatabaseSaveModel
import com.example.roufy235.whatsappclone.R
import com.example.roufy235.whatsappclone.Services.Data
import com.example.roufy235.whatsappclone.Utilities.SHARED_PREFERENCE_LOGIN
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_chat.*

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter : SectionsPagerAdapter? = null


    private lateinit var  mFirebaseAnalytics : FirebaseAnalytics
    private lateinit var mRef : DatabaseReference
    private lateinit var database : FirebaseDatabase

    lateinit var prefs : SharedPreferences



    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))




        prefs = getSharedPreferences(SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE)
        database = FirebaseDatabase.getInstance()
        mRef = database.reference
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)




        fab.setOnClickListener { view ->
            checkMyPermission()
        }
    }

    private fun checkMyPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), 2000)
                return
            }
            LoadContacts()
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        if (requestCode == 300 && data != null && resultCode == Activity.RESULT_OK) {
            val selectedContact = data.data
            val cursor = contentResolver.query(selectedContact, null, null, null, null)

            if (cursor.moveToFirst()){
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val hasPhoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhoneNo == "1"){
                    val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                            null, null)

                    phones.moveToFirst()
                    val phoneNumber1 = phones.getString(phones.getColumnIndex("data1"))
                    val name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))


                    val phoneNumber = phoneNumber1.replace(" ", "")


                    //println(phoneNumber)
                    //check if user has already registered
                    mRef.child("contacts").orderByChild("PhoneNumber").equalTo(phoneNumber).addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(p0 : DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0 : DataSnapshot?) {
                            if (p0 != null) {
                                if (p0.exists()) {
                                    //save
                                    mRef.child("userChatList").child(Data.getPhoneNumber(applicationContext)).child(phoneNumber).setValue(FirebaseDatabaseSaveModel(name, phoneNumber))
                                } else {
                                    mRef.child("userChatList").child(Data.getPhoneNumber(applicationContext)).child(phoneNumber).setValue(FirebaseDatabaseSaveModel(name, phoneNumber))
                                    Toast.makeText(applicationContext, "This account is not available on WhatsAppClone", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(applicationContext, "This account is not available on WhatsAppClone", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })







                    phones.close()

                }
            }

            cursor.close()
        }
    }

    private fun LoadContacts() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, 300)
    }

    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<out String>, grantResults : IntArray) {
        when (requestCode) {
            2000 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LoadContacts()
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu : Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position : Int) : Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1)
            //println(position)
            when (position) {
                0 -> {
                    //fab.setImageDrawable(getDrawable(R.drawable.ic_message_black_24dp))
                    return Chat()
                }
                1 -> {
                    //fab.setImageDrawable(getDrawable(R.drawable.ic_photo_camera_black_24dp))
                    return Status()
                }
                2 -> {
                    //fab.setImageDrawable(getDrawable(R.drawable.user_image))
                    return Calls()
                }
                else -> {
                    return Chat()
                }

            }
        }

        override fun getCount() : Int {
            // Show 3 total pages.
            return 3
        }
    }


}
