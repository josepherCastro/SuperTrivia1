package br.edu.ifpr.josepher.supertrivia1

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_trivia.*
import kotlinx.android.synthetic.main.activity_main.*


class Trivia : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )


        btNavView.setupWithNavController(findNavController(R.id.navHostFragment))
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun showNetworkMessage(isConnected: Boolean) {

//        if (!isConnected) {
//
//            this.snackBar = Snackbar.make(findViewById(R.id.chooseLevelFragment), "You are offline", Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
//            this.snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
//            this.snackBar?.show()
//        } else {
//            this.snackBar?.dismiss()
//        }
    }
}