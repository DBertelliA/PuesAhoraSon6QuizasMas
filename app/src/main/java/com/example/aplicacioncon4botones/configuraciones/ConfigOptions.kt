package com.example.aplicacioncon4botones.configuraciones

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacioncon4botones.R
import com.example.aplicacioncon4botones.databinding.ConfMenuBinding

class ConfigOptions : AppCompatActivity() {
    private lateinit var confgB : ConfMenuBinding

    private lateinit var shareFich : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confgB = ConfMenuBinding.inflate(layoutInflater)
        setContentView(confgB.root)

        ViewCompat.setOnApplyWindowInsetsListener(confgB.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    override fun onStart() {
        super.onStart()

        confgB.fondoSetter.isChecked = true

        confgB.volverDelConf.setOnClickListener {
            val intencion = Intent(this, com.example.aplicacioncon4botones.Activity::class.java)
            intencion.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intencion)
        }
    }

    fun creacionDatos(nombreU : String, paisSelec : String, fondoS : Boolean, fechaS : String, amableS : Boolean){
        shareFich = getSharedPreferences(getString(R.string.fichero_compalta),MODE_PRIVATE)

        var nombre;
        var Pais;
        var Fondo;
        var Fecha;
        var amable;
    }


}