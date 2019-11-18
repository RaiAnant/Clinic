package com.example.clinic

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_report_entry.*
import java.io.File
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class ReportEntry : AppCompatActivity() {


    internal val REQUEST_TAKE_PHOTO = 1
    var storage = FirebaseStorage.getInstance()
    lateinit var imageBitmap : Bitmap


    val REQUEST_IMAGE_CAPTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_entry)
        click.setOnClickListener { dispatchTakePictureIntent() }
    }


    internal lateinit var mCurrentPhotoPath: String
    internal var photoFile: File? = null


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageBitmap = data!!.extras.get("data") as Bitmap
            imageView2.setImageBitmap(imageBitmap)
        }
    }



    //Fire up intent to take photo
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    fun uploadReport(view: View) {
        val storageRef = storage.getReference("/image/")

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = storageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
            it.storage.downloadUrl.addOnCompleteListener {
                Log.d("URL", it.result!!.toString())


                FirebaseDatabase.getInstance().getReference("reports/dhruv/").push().setValue(Report("Dhruv", "Cancer", it.result!!.toString())).addOnCompleteListener {
                    Toast.makeText(applicationContext,"Report Uploaded",Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, AddMedication::class.java)
                    startActivity(intent)
                }


            }
        }

    }
}
