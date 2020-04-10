package com.shrikant.demoapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.shrikant.demoapp.databinding.ItemNewsBinding
import com.shrikant.domain.news.Article

class NewsAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>(), Filterable {

    private var list: ArrayList<Article> = arrayListOf()
    var searchList = list.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        searchList[position].let { holder.setData(it) }
    }

    fun setArticleList(articleList: List<Article>) {
        val start = searchList.size
        this.list.addAll(articleList)
        searchList = list
        notifyItemRangeChanged(start, articleList.size)
    }

    fun updateList(articleList: List<Article>) {
        this.list.addAll(articleList)
        searchList = list
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    interface OnItemClickListener {
        fun onItemClicked(article: Article)
    }

    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(article: Article) {
            with(binding) {
                model = article
                root.setOnClickListener { listener?.onItemClicked(article) }
            }

        }
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                searchList = if (charString.isEmpty()) {
                    list
                } else {
                    val filteredList = ArrayList<Article>()
                    for (item in list) {
                        if (item.title?.contains(charString, true) == true)
                            filteredList.add(item)
                    }

                    filteredList

                }
                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList = results?.values as List<Article>
                notifyDataSetChanged()
            }

        }
    }

}