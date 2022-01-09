package com.kaveri.byjutestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        val mViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
    }

}