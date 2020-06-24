package com.kavya.newsreader.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kavya.newsreader.R
import com.kavya.newsreader.model.network.news.data.Article
import com.kavya.newsreader.utils.Launcher
import com.kavya.newsreader.utils.DateStringManipulator
import kotlinx.android.synthetic.main.news_card.view.*

/**
 * Created by Kavya P S on 17/06/20.
 */

class NewsAdapter(var articleList: ArrayList<Article>, val launcher: Launcher<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            val articleImage: ImageView = itemView.findViewById(R.id.article_image)
            val headline: TextView = itemView.findViewById(R.id.headline)
            val source: TextView = itemView.findViewById(R.id.source)
            val date: TextView = itemView.findViewById(R.id.date)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.itemView.headline.text = articleList[position].mTitle
        holder.itemView.source.text = articleList[position].mSource.mName
        holder.itemView.date.text =
            DateStringManipulator.dateTimezoneRemover(articleList[position].mPublishedAt)
        Glide
            .with(holder.itemView.context)
            .load(articleList[position].mImageUrl)
            .thumbnail(0.5f)
            .into(holder.itemView.article_image);

        if (holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(null)

        }
        val viewList = ArrayList<View>()
        viewList.add(holder.itemView.article_image)
        viewList.add(holder.itemView.headline)
        viewList.add(holder.itemView.source)
        viewList.add(holder.itemView.date)
        holder.itemView.setOnClickListener {
            launcher.launchActivity(articleList[position], viewList)
        }
    }

}