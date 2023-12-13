package com.example.cifradocesarcorrutinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var ed_texto : EditText
    private lateinit var ed_numDespl : EditText

    private lateinit var btnCifrado : Button
    private lateinit var tv_verCifrado : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Introducir Campos
        ed_texto = findViewById(R.id.editTextText)
        ed_numDespl = findViewById(R.id.editTextNumber)

        // Calcular y mostrar Campos
        btnCifrado = findViewById(R.id.btnCifrar)
        tv_verCifrado = findViewById(R.id.tv_verCifrado)


    }
}