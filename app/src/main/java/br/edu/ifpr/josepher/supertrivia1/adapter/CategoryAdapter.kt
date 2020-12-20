package br.edu.ifpr.josepher.supertrivia1.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifpr.josepher.supertrivia1.R
import br.edu.ifpr.josepher.supertrivia1.dao.CategoryDAO
import br.edu.ifpr.josepher.supertrivia1.model.category.Category
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val dao = CategoryDAO()
    private var categories = listOf<Category>()
    private var categorySelect: Int = 0
    private var select: Boolean = false

    init {
        val handler = Handler()
        handler.postDelayed({
            dao.getAll {
                categories = it
                notifyDataSetChanged()
            }
        }, 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        if (categories.isNotEmpty()) {
            val category = categories[position]
            holder.fillView(category)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_category

    override fun getItemCount() = categories.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun fillView(category: Category){
            itemView.txCategory.text = category.name

            itemView.setOnClickListener {
                select = !select
                val position = categories.indexOf(category)
                categorySelect = position
                notifyItemChanged(position)
            }
        }
    }
    fun getCategory(): Category {
        return categories[categorySelect]
    }


}