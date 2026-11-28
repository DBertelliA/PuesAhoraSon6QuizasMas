package com.example.aplicacioncon4botones;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import com.example.aplicacioncon4botones.databinding.ActividadChistesBinding
import java.util.Locale
import kotlin.math.floor

class ActividadChistes : AppCompatActivity() {

    private lateinit var binding : ActividadChistesBinding
    private lateinit var textToSpeech: TextToSpeech
    private val TOUCH_MAX_TIME = 500
    private var touchLastTime: Long = 0
    private var touchNumber = 0
    private lateinit var handler: Handler
    val MYTAG = "LOGCAT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActividadChistesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureTextToSpeech()
        initHander()
        initEvent()
    }

    private fun initHander() {
        handler = Handler(Looper.getMainLooper())
        binding.progressBar.visibility = View.VISIBLE
        binding.btnExample.visibility = View.GONE

        Thread{
            Thread.sleep(3000)
            handler.post{
                binding.progressBar.visibility = View.GONE
                val description = getString(R.string.describe).toString()
                speakMeDescription(description)
                Log.i(MYTAG,"Se ejecuta correctamente el hilo")
                binding.btnExample.visibility = View.VISIBLE

            }
        }.start()
    }



    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if(it != TextToSpeech.ERROR){
                textToSpeech.language = Locale.getDefault()

                Log.i(MYTAG,"Sin problemas en la configuración TextToSpeech")
            }else{
                Log.i(MYTAG,"Error en la configuración TextToSpeech")
            }
        })
    }


    private fun initEvent() {
        binding.btnExample.setOnClickListener{
            val chiste = numeroAleConChiste()

            val currentTime = System.currentTimeMillis()

            if (currentTime - touchLastTime < TOUCH_MAX_TIME){

                executorDoubleTouch(chiste)
                Log.i(MYTAG,"Escuchamos el chiste")
            }
            else{
                Log.i(MYTAG,"Hemos pulsado 1 vez.")
                speakMeDescription("Presiona dos veces el boton, esto no va dirigido a un ciego")
            }

            touchLastTime = currentTime
        }

        binding.btnBack.setOnClickListener {
            speakMeDescription(" ")
            val intencion = Intent(this, Activity::class.java)
            intencion.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intencion)
        }
    }


    private fun speakMeDescription(s: String) {
        Log.i(MYTAG,"Intenta hablar")
        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun numeroAleConChiste(): String{
        var i : Int = (floor(Math.random() * 10)).toInt()
        when(i) {
            0 -> return resources.getString(R.string.chiste1);
            1 -> return resources.getString(R.string.chiste2);
            2 -> return resources.getString(R.string.chiste3);
            3 -> return resources.getString(R.string.chiste4);
            4 -> return resources.getString(R.string.chiste5);
            5 -> return resources.getString(R.string.chiste6);
            6 -> return resources.getString(R.string.chiste7);
            7 -> return resources.getString(R.string.chiste8);
            8 -> return resources.getString(R.string.chiste9);
            9 -> return resources.getString(R.string.chiste10);
        }
        return "Error en el chiste"
    }

    private fun executorDoubleTouch(chiste: String) {
        speakMeDescription(chiste)
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized){
            textToSpeech.stop()
            textToSpeech.shutdown()

        }
        super.onDestroy()
    }
}