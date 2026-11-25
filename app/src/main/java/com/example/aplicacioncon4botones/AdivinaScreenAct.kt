package com.example.aplicacioncon4botones;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
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

    private var booleano = false;

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
            if(!booleano) {
                confBind.txtResultado.visibility = View.VISIBLE
                launcherAsync()  //comienza el juego
            }else{
                Toast.makeText(this, "No puedes volver a pulsar hasta que se acabe la accion",
                    Toast.LENGTH_LONG).show()
            }
        }
        confBind.backInit.setOnClickListener {
            if(!booleano) {
                intent = Intent(this, Activity::class.java).apply {
                    Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                startActivity(intent)
            }else{
                Toast.makeText(this, "No puedes volver hasta que se acabe la accion",
                    Toast.LENGTH_LONG).show()
            }
        }
    }


    fun launcherAsync(){
        var hiloAsync = Executors.newSingleThreadScheduledExecutor()
        var msc = 1000


        //un hilo para mostrar los cambios de imagenes simulando una tirada de dado
        for(i in 1..5){
            hiloAsync.schedule({
                booleano = true
                throwInTime()
            },
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
        verifier(suma)
        booleano = false;
        runOnUiThread{
            if(numerin == 1){
                Toast.makeText(this@AdivinaScreenAct, "Has adivinado el numero", Toast.LENGTH_LONG).show()
                intent = Intent(this, VictoryScreen::class.java).apply {
                    Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                startActivity(intent)
            }else if (numerin == 0){
                Toast.makeText(this@AdivinaScreenAct, "No has adivinado el numero", Toast.LENGTH_LONG).show()
            }else if(numerin == -1){
                Toast.makeText(this@AdivinaScreenAct, "Ta vacio", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun verifier(sum: Int) {
        var valorC = confBind.NumeroField.text.toString().toIntOrNull()

        if(valorC != null) {
            if (suma == valorC) {
                numerin = 1
            } else {
                numerin = 0
            }
        }else{
            numerin = -1
        }


    }


}
