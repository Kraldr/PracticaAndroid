package com.example.primera

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar;
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Inicio"

        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val button = findViewById<Button>(R.id.btnIngresar)

        if (loadData()) {
            val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
            val saveEmail: String = sharedPreferences.getString("correo", null).toString()
            val savePass: String = sharedPreferences.getString("pass", null).toString()
            start(saveEmail, savePass)
        }


        button.setOnClickListener {
            var correo = txtCorreo.text.toString();
            var contrasena = txtContrasena.text.toString();
            saveData(correo, true, contrasena)
            start(correo, contrasena)
        }
    }


    private fun start (correo: String, contrasena: String) {
        val result:Boolean = correo.contains("@")
        if (result) {
            if (contrasena.isEmpty()) {
                Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_LONG).show()
            }else {
                val intent = Intent(this, menuList::class.java).apply {

                }
                //Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_LONG).show()
                startActivity(intent)
                finish()
            }
        }else {
            Toast.makeText(this, "Ingresa un correo valido", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveData (correo:String, online:Boolean, pass: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("correo", correo)
            putString("pass", pass)
            putBoolean("online", online)
        }.apply()
    }

    private fun loadData (): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: String = sharedPreferences.getString("correo", null).toString()
        val saveOnline: Boolean = sharedPreferences.getBoolean("online", false)
        val savePass: String = sharedPreferences.getString("pass", null).toString()
        return (saveOnline)
    }
}