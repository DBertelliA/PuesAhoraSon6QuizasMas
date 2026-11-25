package com.example.aplicacioncon4botones

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacioncon4botones.databinding.VictoriaBinding

class VictoryScreen : AppCompatActivity() {
    private lateinit var confB : VictoriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confB = VictoriaBinding.inflate(layoutInflater)
        setContentView(confB.root)

        enableEdgeToEdge()
        init()
    }

    fun init(){
        confB.buttonVolver.setOnClickListener {
            intent = Intent(this, AdivinaScreenAct::class.java).apply {
                Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
        }
    }

}