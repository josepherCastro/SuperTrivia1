package br.edu.ifpr.josepher.supertrivia1.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import br.edu.ifpr.josepher.supertrivia1.R
import br.edu.ifpr.josepher.supertrivia1.model.game.endgame.EndGameData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_end_game.view.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Suppress("UNREACHABLE_CODE")
class EndGameFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_end_game, container, false)

        val gson = Gson()

        val game = arguments?.getString("endGame")

        val endGame = gson.fromJson(game, EndGameData::class.java)


        val s = ZonedDateTime.parse(endGame.game.started_at)
        val f = ZonedDateTime.parse(endGame.game.finished_at)


        val formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")
        val startedAt = s.format(formatter)
        val finishedAt = f.format(formatter)

        view.txtStartedAt.text = getString(R.string.started)+" "+startedAt
        view.txtFineshedAt.text = getString(R.string.finished)+" "+finishedAt
        view.txtEndScore.text = getString(R.string.endScore)+endGame.game.score.toString()


        return view
    }
}