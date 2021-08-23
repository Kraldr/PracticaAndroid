package com.example.primera

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.primera.databinding.ActivityRegistroBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class registro : AppCompatActivity() {

    var completed: String = "";
    var duplicated: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val saveEmail: String = sharedPreferences.getString("correo", null).toString()

        title = "Email: $saveEmail"

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.booleano,
            android.R.layout.simple_spinner_dropdown_item
        )

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                completed = parent!!.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        btnRegistrar.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(ApiService::class.java)


            api.fecthAllUsers().enqueue(object : Callback<List<Todos>> {
                override fun onResponse(call: Call<List<Todos>>, response: Response<List<Todos>>) {
                    val datos: List<Todos> = response.body();
                    validar(datos)
                }

                override fun onFailure(call: Call<List<Todos>>?, t: Throwable?) {
                    Log.d("x", "onFailure")
                }

            })


        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, menuList::class.java).apply {

        }
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                saveData("",false,"")
                Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_LONG).show()
                startActivity(intent)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
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

    private fun validar (datos: List<Todos>) {
        duplicated = false

        val txtUserIdR = findViewById<EditText>(R.id.txtUserIdR)
        val txtIdR = findViewById<EditText>(R.id.txtIdR)
        val txtTitleR = findViewById<EditText>(R.id.txtTitleR)

        if (txtUserIdR.text.toString() == "") {
            Toast.makeText(this@registro, "Ingresar USERID", Toast.LENGTH_LONG).show()
        }else if (txtIdR.text.toString() == "") {
            Toast.makeText(this@registro, "Ingresar ID", Toast.LENGTH_LONG).show()
        }else if (txtTitleR.text.toString() == ""){
            Toast.makeText(this@registro, "Ingresar Title", Toast.LENGTH_LONG).show()
        }else if (completed == "Seleccione una opción") {
            Toast.makeText(this@registro, "Por favor seleccione true o false", Toast.LENGTH_LONG).show()
        }else {
            val ID: String = txtIdR.text.toString()


            for (element in datos) {
                if (ID == element.id.toString()) {
                    duplicated = true
                }
            }

            if (!duplicated){
                Toast.makeText(this@registro, "Registrado", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this@registro, "No se puede registrar por que el ID $ID ya existe", Toast.LENGTH_LONG).show()
            }
        }

    }

}