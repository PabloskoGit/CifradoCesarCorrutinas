package com.example.cifradocesarcorrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var ed_texto: EditText
    private lateinit var ed_numDespl: EditText

    private lateinit var btnCifrado: Button
    private lateinit var tv_verCifrado: TextView

    private lateinit var progresoCalculo: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Introducir Campos
        ed_texto = findViewById(R.id.editTextText)
        ed_numDespl = findViewById(R.id.editTextNumber)

        // Calcular y mostrar Campos
        btnCifrado = findViewById(R.id.btnCifrar)
        tv_verCifrado = findViewById(R.id.tv_verCifrado)

        // Progress Bar
        progresoCalculo = findViewById(R.id.pb_calculo)

        btnCifrado.setOnClickListener {
            val texto = ed_texto.text.toString()
            val numDespl = ed_numDespl.text.toString().toIntOrNull()

            numDespl?.let {
                cifrarTextoCorrutina(texto, it)
            }
        }
    }

    private fun cifrarTextoCorrutina(texto: String, numDespl: Int) {
        progresoCalculo.visibility = android.view.View.VISIBLE
        progresoCalculo.max = texto.length

        CoroutineScope(Dispatchers.Main).launch {

            val cifrado = withContext(Dispatchers.Default) {
                cifrarTexto(texto, numDespl) { progreso ->

                    launch(Dispatchers.Main) {
                        progresoCalculo.progress = progreso
                    }
                }
            }
            tv_verCifrado.text = cifrado.joinToString("")
            progresoCalculo.visibility = android.view.View.INVISIBLE
        }

    }

    // Función para cifrar el texto, la letra cifrada se obtiene sumando el número de desplazamiento a la letra introducida,
    // si en la cadena hay un numero no se cifra, si se pasa de la z se vuelve a empezar desde la a. Si hay un espacio tampoco se cifra
    private fun cifrarTexto(
        texto: String,
        numDespl: Int,
        actualizarProgreso: (Int) -> Unit
    ): List<String> {

        // Lista para guardar el texto cifrado
        val cifrado = mutableListOf<String>()
        // Posición de la letra en el texto
        var posicionCharletra = 0

        // Mientras que el tamaño de la lista sea menor que el tamaño del texto
        while (cifrado.size < texto.length) {
            // Se obtiene el caracter de la posición
            val char = texto[posicionCharletra]
            // Si el caracter es una letra se cifra y se añade a la lista
            if (char.isLetter()) {
                // Se obtiene la base de la letra, si es mayúscula o minúscula para poder cifrarla correctamente y se añade a la lista
                val base = if (char.isUpperCase()) 'A' else 'a'
                val letraCifrada = ((char - base + numDespl) % 26 + base.code).toChar()
                cifrado.add(letraCifrada.toString())

                // Si el caracter es un número o un espacio se añade a la lista
            } else if (char.isDigit() || char.isWhitespace()) {
                cifrado.add(char.toString())
            }
            // Se actualiza el progreso
            actualizarProgreso(cifrado.size)
            posicionCharletra++
        }
        return cifrado
    }

    // Función para verificar si un caracter es una letra
    private fun esLetra(letra: Char): Boolean {
        return letra in 'a'..'z' || letra in 'A'..'Z'
    }

}