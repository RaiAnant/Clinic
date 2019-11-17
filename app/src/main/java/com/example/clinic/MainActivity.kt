package com.example.clinic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var count_of_clicks = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn_edit =
            findViewById(R.id.floatingActionButton) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn_edit.setOnClickListener {

        }
    }
}