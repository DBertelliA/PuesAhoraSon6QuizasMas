package com.example.aplicacioncon4botones.configuraciones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacioncon4botones.databinding.ConfMenuBinding

class ConfigOptions : AppCompatActivity() {
    private lateinit var confgB : ConfMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confgB = ConfMenuBinding.inflate(layoutInflater)
        setContentView(confgB.root)


    }


}