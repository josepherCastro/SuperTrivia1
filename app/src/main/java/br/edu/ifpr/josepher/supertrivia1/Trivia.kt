package br.edu.ifpr.josepher.supertrivia1

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_trivia.*
import kotlinx.android.synthetic.main.activity_main.*


class Trivia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)

        btNavView.setupWithNavController(findNavController(R.id.navHostFragment))

        fabExit.setOnClickListener { logout() }
    }
    private fun logout() {
        val sharedPref = this?.getSharedPreferences("user", Context.MODE_PRIVATE)

        sharedPref?.edit()?.putString("password", "")?.putString("email", "")?.putString("token", "")
                ?.apply()


        val intent = Intent(this, ConnectivityReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}