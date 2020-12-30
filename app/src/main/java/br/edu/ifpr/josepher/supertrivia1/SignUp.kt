package br.edu.ifpr.josepher.supertrivia1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import br.edu.ifpr.josepher.supertrivia1.dao.UserDAO
import br.edu.ifpr.josepher.supertrivia1.model.user.UserInput
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btCancel.setOnClickListener { cancel() }
        btSubmit.setOnClickListener {
            submit(
                etxName.text.toString(),
                etxEmailRegistry.text.toString(),
                etxPasswordRegistry.text.toString(),
                etxPasswordRegistryConfirmation.text.toString()
            )
        }
    }
    private fun cancel(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun submit(name: String, email: String, password: String, passwordConfirm: String){
        if(name==""||email==""||password==""||passwordConfirm=="") {
            Toast.makeText(this, R.string.registry_empty, Toast.LENGTH_SHORT).show()
        }else {
            if (password != passwordConfirm){
                Toast.makeText(this, R.string.registry_password_match, Toast.LENGTH_SHORT).show()
            }else{
                val dao = UserDAO()
                val user = UserInput(name, email, password)
                dao.insert(user) {
                    val build: AlertDialog.Builder = AlertDialog.Builder(this)
                    build.setTitle(R.string.registry_success_title)
                    build.setMessage(R.string.registry_success_msg)

                    val alertDialog: AlertDialog = build.create()
                    alertDialog.show()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}