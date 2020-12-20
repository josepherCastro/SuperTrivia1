package br.edu.ifpr.josepher.supertrivia1.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifpr.josepher.supertrivia1.R
import br.edu.ifpr.josepher.supertrivia1.adapter.AnswerAdapter
import br.edu.ifpr.josepher.supertrivia1.dao.GameDAO
import br.edu.ifpr.josepher.supertrivia1.dao.QuestionDAO
import br.edu.ifpr.josepher.supertrivia1.model.question.Answer
import br.edu.ifpr.josepher.supertrivia1.model.question.QuestionData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_play_game.view.*

@Suppress("UNREACHABLE_CODE")
class PlayGameFragment : Fragment() {

    lateinit var answerAdapter: AnswerAdapter
    var dao = GameDAO()
    var daoQ = QuestionDAO()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val returnView = inflater.inflate(R.layout.fragment_play_game, container, false)

        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        val token = sharedPref?.getString("token", "")
        val withSetup = arguments?.getBoolean("withSetup")

        if (token != null && withSetup == true) {

            val gson = Gson()

            val questionJson = arguments?.getString("question")

            val question = gson.fromJson(questionJson, QuestionData::class.java)



            if (question != null) {
                answerAdapter = AnswerAdapter()

                answerAdapter.setAnswers(question.problem.answers)

                returnView.rcListAlternatives.adapter = answerAdapter

                returnView.rcListAlternatives.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

                returnView.txQuestion.text = question.problem.question
            }


        } else {
            val build: AlertDialog.Builder = AlertDialog.Builder(activity)
            build.setCancelable(false)

            val alertDialog: AlertDialog = build.create()
            alertDialog.show()

            var question: QuestionData

            if (token != null) {
                daoQ.existQuestion(token) {


                    alertDialog.dismiss()

                    question = it
                    answerAdapter = AnswerAdapter()

                    answerAdapter.setAnswers(question.problem.answers)

                    returnView.rcListAlternatives.adapter = answerAdapter

                    returnView.rcListAlternatives.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

                    returnView.txQuestion.text = question.problem.question
                }
            }

        }
        returnView.btNext.setOnClickListener {
            nextToQuestion(
                token,
                answerAdapter.getAnswer(),
            )
        }
        return returnView
    }

    private fun nextToQuestion(token: String?, answer: Answer?) {

        if (answer != null && token != null) {

            val build: AlertDialog.Builder = AlertDialog.Builder(activity)
            val buildII: AlertDialog.Builder = AlertDialog.Builder(activity)
            build.setCancelable(false)

            buildII.setPositiveButton(getString(R.string.game_next)) { dialog, _ ->

                daoQ.nextQuestion(token){
                    val bundle = Bundle()

                    val gson = Gson()
                    val questionJson = gson.toJson(it)

                    bundle.putBoolean("withSetup", true)
                    bundle.putString("question", questionJson)

                    findNavController().navigate(R.id.playGameFragment2, bundle)

                    dialog.dismiss()
                }

            }
            buildII.setNegativeButton(getString(R.string.game_finish)){ dialog, _ ->

                dao.endGame(token){
                    findNavController().navigate(R.id.mainFragment)
                }
                dialog.dismiss()
            }

            val alertDialog: AlertDialog = build.create()
            val showUser: AlertDialog = buildII.create()

            alertDialog.show()

            daoQ.answerQuestion(answer.order, token) {
                alertDialog.dismiss()

                if (it.answer.status == "incorrect") {
                    showUser.setTitle(getString(R.string.game_incorrect))
                } else {
                    showUser.setTitle(getString(R.string.game_incorrect))
                }
                showUser.setCancelable(false)
                showUser.show()

                Log.i("answer", it.answer.correct_answer.toString())
                Log.i("answer", it.answer.status)


            }
        } else {
            Toast.makeText(activity, getString(R.string.game_select_alternative), Toast.LENGTH_SHORT)
                .show()
        }
    }
}