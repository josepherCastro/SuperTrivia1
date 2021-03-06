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
    var daoGAME = GameDAO()
    var daoQuestions = QuestionDAO()

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
                daoQuestions.existQuestion(token) {


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
        val bundle = Bundle()
        if (answer != null && token != null) {

            val build: AlertDialog.Builder = AlertDialog.Builder(activity)
            val buildII: AlertDialog.Builder = AlertDialog.Builder(activity)
            build.setCancelable(false)

            buildII.setPositiveButton(getString(R.string.game_next)) { dialog, _ ->

                daoQuestions.nextQuestion(token){


                    val gson = Gson()
                    val questionJson = gson.toJson(it)

                    bundle.putBoolean("withSetup", true)
                    bundle.putString("question", questionJson)

                    findNavController().navigate(R.id.playGameFragment2, bundle)

                    dialog.dismiss()
                }

            }
            buildII.setNegativeButton(getString(R.string.game_finish)){ dialog, _ ->

                daoGAME.endGame(token){
                    val gson = Gson()
                    val endGame = gson.toJson(it)
                    bundle.putString("endGame", endGame)

                    findNavController().navigate(R.id.endGameFragment,bundle)
                }
                dialog.dismiss()
            }

            val alertDialog: AlertDialog = build.create()
            val showUser: AlertDialog = buildII.create()

            alertDialog.show()

            daoQuestions.answerQuestion(answer.order, token) {
                alertDialog.dismiss()

                if (it.answer.status == "incorrect") {
                    showUser.setTitle(getString(R.string.game_incorrect))
                } else {
                    showUser.setTitle(getString(R.string.game_correct))
                }

                showUser.setMessage(getString(R.string.endScore) + " " + it.answer.score.toString())
                showUser.setCancelable(false)
                showUser.show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.game_select_alternative), Toast.LENGTH_SHORT)
                .show()
        }
    }
}