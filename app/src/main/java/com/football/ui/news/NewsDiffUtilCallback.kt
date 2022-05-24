package com.football.ui.news

import androidx.recyclerview.widget.DiffUtil
import com.football.model.News

class NewsDiffUtilCallback: DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
        oldItem == newItem
}