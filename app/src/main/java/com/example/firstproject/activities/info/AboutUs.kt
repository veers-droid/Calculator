package com.example.firstproject.activities.info

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.firstproject.R
import com.example.firstproject.activities.MainActivity

class AboutUs : AppCompatActivity()
{
    override fun onStart()
    {
        super.onStart()
        setContentView(R.layout.about_us)
    }

    fun goBack(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}