package com.example.camerademo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.camerademo.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null

    companion object{
        //for permission
        private const val CAMERA_PERMISSION_CODE=1
        //for intent
        private const val CAMERA_REQUEST_CODE =2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.btnCamera?.setOnClickListener {
            //check if we have the permission
            if(ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
            ){
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,CAMERA_REQUEST_CODE)
            }else{
                //asking for a request
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }

        }

    }
    //to execute when we get a permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,CAMERA_REQUEST_CODE)
            }else{
                Toast.makeText(this,"Oops you just denied the permission for camera." +
                        "Don't worry you can allow it in the settings.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    // add the picture that picked by camera into layout
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode== CAMERA_REQUEST_CODE){
                val thumBnail: Bitmap =data!!.extras!!.get("data")as Bitmap
                binding?.ivImage?.setImageBitmap(thumBnail)
            }
        }
    }

}