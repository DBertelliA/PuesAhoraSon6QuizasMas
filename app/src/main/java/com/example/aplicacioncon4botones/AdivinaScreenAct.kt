package com.example.aplicacioncon4botones;

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicacioncon4botones.databinding.DadosMainGamerBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws
import kotlin.random.Random

class AdivinaScreenAct : AppCompatActivity(){

    private lateinit var confBind : DadosMainGamerBinding
    private lateinit var listaImagenes : List<ImageView>

    private var suma = 0

    private var boolean = false;

    private var numerin = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confBind = DadosMainGamerBinding.inflate(layoutInflater)
        setContentView(confBind.root)

        ViewCompat.setOnApplyWindowInsetsListener(confBind.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    fun init(){
        confBind.txtResultado.visibility = View.VISIBLE;

        confBind.imageButton.setOnClickListener {
            suma = 0
            launcherAsync()
        }
    }


    fun launcherAsync(){
        var hiloAsync = Executors.newSingleThreadScheduledExecutor()
        var msc = 1000


        //un hilo para mostrar los cambios de imagenes simulando una tirada de dado
        for(i in 1..5){
            hiloAsync.schedule({ throwInTime() },
                msc * i.toLong(), TimeUnit.MILLISECONDS)
        }

        //hilo que muestra la solucion
        hiloAsync.schedule({viewResult()},7.toLong(), TimeUnit.SECONDS)

        hiloAsync.shutdown() //No mas

    }


    fun throwInTime(){
        var listaNumero = Array(3){
            Random.nextInt(1,6)
        }
        listaImagenes = listOf(
            confBind.imagviewDado1,
            confBind.imagviewDado2,
            confBind.imagviewDado3
        )
        //No lo habia entendido pero ahora si, la lista esa, genera 3 numeros directos y los suma
        //de manera instantanea para evitar recalls

        suma = listaNumero.sum()
        for(i in 0..3){
            swicherAnimado(listaImagenes.get(i), listaNumero[i])
        }
    }


    fun swicherAnimado(imgV : ImageView, number : Int){
        when(number){
            1-> imgV.setImageResource(R.drawable.dado1)
            2-> imgV.setImageResource(R.drawable.dado2)
            3-> imgV.setImageResource(R.drawable.dado3)
            4-> imgV.setImageResource(R.drawable.dado4)
            5-> imgV.setImageResource(R.drawable.dado5)
            6-> imgV.setImageResource(R.drawable.dado6)
        }
    }

    fun viewResult(){
        confBind.txtResultado.text = suma.toString()
    }








}
