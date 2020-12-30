package br.edu.ifpr.josepher.supertrivia1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifpr.josepher.supertrivia1.R
import br.edu.ifpr.josepher.supertrivia1.adapter.RankingAdapter
import kotlinx.android.synthetic.main.fragment_ranking.view.*

class RankingFragment : Fragment() {
    private lateinit var adapter : RankingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val returnView = inflater.inflate(R.layout.fragment_ranking, container, false)

        adapter = RankingAdapter()
        returnView.rcListScore.adapter = adapter

        returnView.rcListScore.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        return returnView
    }
}