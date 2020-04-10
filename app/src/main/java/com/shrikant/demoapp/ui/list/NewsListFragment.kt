package com.shrikant.demoapp.ui.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikant.demoapp.R
import com.shrikant.demoapp.basic.PaginationScrollListener
import com.shrikant.demoapp.databinding.FragmentNewsListBinding
import com.shrikant.domain.news.Article
import com.shrikant.network.extension.EventObserver
import com.shrikant.network.utils.Constant

class NewsListFragment : Fragment(),
    NewsAdapter.OnItemClickListener {

    private lateinit var mSearchView: SearchView
    private var adapter: NewsAdapter? = null
    private var binding: FragmentNewsListBinding? = null
    private lateinit var viewModel: NewsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        viewModel.page = 0
        setHasOptionsMenu(true)


        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        viewModel.newsListObserver.observe(viewLifecycleOwner, EventObserver {
            adapter?.setArticleList(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            binding?.progressBar?.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.failureObserver.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        //getting data
        viewModel.callNewsList(true)

    }

    //init recycler view
    private fun initAdapter() {
        adapter = NewsAdapter(this)
        binding?.rvRecycler?.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding?.rvRecycler?.layoutManager = layoutManager
        binding?.rvRecycler?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return viewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading
            }

            override fun loadMoreItems() {
                if (mSearchView.query.toString().isNotEmpty())
                    return
                viewModel.isLoading = true
                viewModel.page++
                viewModel.callNewsList(false)
            }
        })
    }

    //recycler item click listener
    override fun onItemClicked(article: Article) {
        val bundle = Bundle().apply {
            putSerializable(Constant.KEY_ARTICLE, article)
        }
        findNavController(this).navigate(R.id.newsDetailsFragment, bundle)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val mSearch = menu.findItem(R.id.search)
        mSearchView = mSearch?.actionView as SearchView
        mSearchView.maxWidth = Integer.MAX_VALUE
        mSearchView.queryHint = "Search here..."
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.text = query
                adapter?.filter?.filter(query)
                return false
            }
        })

    }
}
