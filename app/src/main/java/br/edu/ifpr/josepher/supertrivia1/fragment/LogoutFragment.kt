package br.edu.ifpr.josepher.supertrivia1.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifpr.josepher.supertrivia1.ConnectivityReceiver
import br.edu.ifpr.josepher.supertrivia1.R
import kotlinx.android.synthetic.main.activity_trivia.*
import kotlinx.android.synthetic.main.fragment_logout.view.*

class LogoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_logout, container, false)

        view.btLogout.setOnClickListener { logout() }
        return view
    }

    private fun logout() {
        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        sharedPref?.edit()?.putString("password", "")?.putString("email", "")?.putString("token", "")
            ?.apply()

        val intent = Intent(activity, ConnectivityReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}