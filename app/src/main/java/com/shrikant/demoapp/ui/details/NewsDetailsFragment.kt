package com.shrikant.demoapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shrikant.demoapp.basic.setImageViewResource
import com.shrikant.demoapp.databinding.FragmentNewsDetailsBinding
import com.shrikant.domain.news.Article
import com.shrikant.network.utils.Constant

class NewsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailsFragment()
    }

    private var binding: FragmentNewsDetailsBinding? = null
    private lateinit var viewModel: NewsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsDetailsViewModel::class.java)
        arguments?.let {
            updateUI(it.getSerializable(Constant.KEY_ARTICLE) as Article)
        }
    }

    private fun updateUI(article: Article) {
        binding?.let {
            it.tvTitle.text = article.title
            it.tvDesc.text = article.description
            it.tvAuthor.text = article.author
            setImageViewResource(it.ivBanner, article.urlToImage)
        }
    }

}
