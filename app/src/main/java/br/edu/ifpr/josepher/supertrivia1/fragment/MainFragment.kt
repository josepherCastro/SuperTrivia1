package br.edu.ifpr.josepher.supertrivia1.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifpr.josepher.supertrivia1.ConnectivityReceiver
import br.edu.ifpr.josepher.supertrivia1.MainActivity
import br.edu.ifpr.josepher.supertrivia1.R
import br.edu.ifpr.josepher.supertrivia1.adapter.CategoryAdapter
import br.edu.ifpr.josepher.supertrivia1.dao.CategoryDAO
import br.edu.ifpr.josepher.supertrivia1.dao.GameDAO
import br.edu.ifpr.josepher.supertrivia1.dao.QuestionDAO
import br.edu.ifpr.josepher.supertrivia1.model.Difficulty
import br.edu.ifpr.josepher.supertrivia1.model.category.Category
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val returnView = inflater.inflate(R.layout.fragment_main, container, false)

        val dao = CategoryDAO()
        adapter = CategoryAdapter()

        returnView.rcListCategories.adapter = adapter

        returnView.rcListCategories.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        returnView.fabPlayGame.setOnClickListener {
            setSetup(
                adapter.getCategory(),
                onRadioButtonClicked(returnView)
            )
        }
        return returnView
    }
    private fun onRadioButtonClicked(viewRadio: View): Int {
        if (viewRadio is RadioButton) {
            // Is the button now checked?
            val checked = viewRadio.isChecked

            // Check which radio button was clicked
            when (viewRadio.getId()) {
                R.id.rBtEasy ->
                    if (checked) {
                        return 1
                    }
                R.id.rBtMedium ->
                    if (checked) {
                        return 2
                    }
                R.id.rBtHard ->
                    if (checked) {
                        return 3
                    }
            }
        }
        return 0
    }

    private fun setSetup(category: Category?, opDifficulty: Int) {
        val daoGame = GameDAO()
        val daoQuestion = QuestionDAO()

        val difficulty = Difficulty(opDifficulty)

        if (category != null) {

            val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
            val token = sharedPref?.getString("token", "")

            if (token != null) {

                val build: AlertDialog.Builder = AlertDialog.Builder(activity)

                build.setCancelable(false)

                val alertDialog: AlertDialog = build.create()
                alertDialog.show()

                daoGame.startGameWhitSetup(token, difficulty.difficulty, category.id) {
                    Log.i("DF", difficulty.difficulty)

                    daoQuestion.nextQuestion(token) {
                        val bundle = Bundle()

                        val gson = Gson()
                        val questionJson = gson.toJson(it)

                        bundle.putBoolean("withSetup", true)
                        bundle.putString("question", questionJson)

                        alertDialog.dismiss()
                        findNavController().navigate(R.id.playGameFragment2, bundle)
                    }
                    daoQuestion.existQuestion(token) {
                        alertDialog.dismiss()
                        Toast.makeText(activity,getString(R.string.game_running), Toast.LENGTH_SHORT).show()
                    }

                }

            }
        } else {
            Toast.makeText(activity, getString(R.string.error_category), Toast.LENGTH_SHORT)
                .show()
        }
    }
}