package br.edu.ifpr.josepher.supertrivia1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btSignUp = findViewById<Button>(R.id.btSignUp)
        btSignUp.setOnClickListener{signUp()}
        val btSignIn = findViewById<Button>(R.id.btSignIn)
        btSignIn.setOnClickListener{signIn()}


    }
    private fun signUp(){
        startActivity(Intent(this, SignUp::class.java))
    }
    private fun signIn(){
        Toast.makeText(this, "Em desenvolvimento...", Toast.LENGTH_SHORT).show()
    //startActivity(Intent(this, SignUp::class.java))
    }
}