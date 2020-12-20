package br.edu.ifpr.josepher.supertrivia1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifpr.josepher.supertrivia1.R
import br.edu.ifpr.josepher.supertrivia1.model.question.Answer
import kotlinx.android.synthetic.main.item_alternative.view.*

class AnswerAdapter () : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    private var alternatives = listOf<Answer>()

    private var alternativeSelect: Int = 0
    private var select: Boolean = false


    override fun getItemViewType(position: Int) = R.layout.item_alternative

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (alternatives.isNotEmpty()) {
            val alternative = alternatives[position]
            holder.fillView(alternative)
        }
    }

    override fun getItemCount() = alternatives.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(alternative: Answer) {

            itemView.txIndex.text = alternative.order.toString()
            itemView.txAlternative.text = alternative.description
            itemView.setOnClickListener {
                select = !select
                val position = alternatives.indexOf(alternative)
                alternativeSelect = position
                notifyItemChanged(position)
            }
        }
    }
    fun setAnswers(list: List<Answer>) {
        alternatives = list
        notifyDataSetChanged()
    }

    fun getAnswer(): Answer {
        return alternatives[alternativeSelect]
    }
}