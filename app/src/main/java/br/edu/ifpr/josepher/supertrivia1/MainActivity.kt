package br.edu.ifpr.josepher.supertrivia1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import br.edu.ifpr.josepher.supertrivia1.dao.UserDAO
import br.edu.ifpr.josepher.supertrivia1.model.user.UserLogin
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSignUp.setOnClickListener{signUp()}
        btSignIn.setOnClickListener{signIn(etxEmail.text.toString(), etxPassword.text.toString())}


    }
    private fun signUp(){
        startActivity(Intent(this, SignUp::class.java))
    }
    private fun signIn(email: String, password: String, verify: Boolean = false) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val dao = UserDAO()
            val userLogin = UserLogin(email, password)

            try {
                dao.login(userLogin) {
                    Log.i("LOGIN", it.toString())

                    val sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE)

                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString("email", it.email)
                            putString("password", password)
                            putString("token", it.token)
                            commit()
                        }
                    }
                    Log.i("LOGIN", it.toString())
                    startActivity(Intent(this, Trivia::class.java))

                }
            } catch (e: Exception) {
                Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
        }
    }
}