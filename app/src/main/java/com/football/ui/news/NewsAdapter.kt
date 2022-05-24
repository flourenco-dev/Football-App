package com.football.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.football.databinding.ItemNewsBinding
import com.football.model.News

class NewsAdapter(
    private val listener: Listener
): ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(currentList[position])
    }

    fun setNewsItems(newListOfNewsItems: List<News>) {
        submitList(newListOfNewsItems)
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bindView(news: News) {
            binding.apply {
                newsTitle.text = news.title
                newsImage.load(news.imageUrl)
                newsResourceIconImage.load(news.resourceUrl)
                newsResourceNameText.text = news.resourceName
                root.setOnClickListener {
                    listener.onNewsClicked(news)
                }
            }
        }
    }

    interface Listener {
        fun onNewsClicked(news: News)
    }
}
