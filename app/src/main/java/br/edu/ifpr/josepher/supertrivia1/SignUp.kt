package br.edu.ifpr.josepher.supertrivia1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.edu.ifpr.josepher.supertrivia1.dao.UserDAO
import br.edu.ifpr.josepher.supertrivia1.model.user.UserInput
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.delay

class SignUp : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btCancel.setOnClickListener { home() }
        btSubmit.setOnClickListener {
            submit(
                etxName.text.toString(),
                etxEmailRegistry.text.toString(),
                etxPasswordRegistry.text.toString(),
                etxPasswordRegistryConfirmation.text.toString()
            )
            home()
        }
    }
    private fun home(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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
                try {
                    dao.insert(user) {
                        val build: AlertDialog.Builder = AlertDialog.Builder(this)
                        build.setTitle(R.string.registry_success_title)
                        build.setMessage(R.string.registry_success_msg)

                        build.setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                    }
                }catch (e: Exception){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}