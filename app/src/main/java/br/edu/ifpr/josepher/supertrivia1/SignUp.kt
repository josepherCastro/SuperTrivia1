package br.edu.ifpr.josepher.supertrivia1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btCancel = findViewById<Button>(R.id.btCancel)
        btCancel.setOnClickListener{cancel()}
        val btSubmit = findViewById<Button>(R.id.btSubmit)
        btSubmit.setOnClickListener{submit()}


    }
    private fun cancel(){
        startActivity(Intent(this, MainActivity::class.java))
    }
    private fun submit(){
        Toast.makeText(this, "Em desenvolvimento...", Toast.LENGTH_SHORT).show()
    }
}