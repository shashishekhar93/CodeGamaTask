package com.tech.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.myapplication.data.utils.Resource
import com.tech.myapplication.databinding.FragmentNewsBinding
import com.tech.myapplication.presentation.adapter.FeaturedAdapter
import com.tech.myapplication.presentation.adapter.NewsAdapter
import com.tech.myapplication.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var featuredAdapter: FeaturedAdapter
    private var country = "us"
    private var page = 1

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    private var isSearchMode = false


    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.newsRV.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                if (isSearchMode) {
                    viewModel.searchNews(country, binding.svNews.query.toString(), page)
                } else {
                    viewModel.getNewsHeadlines(country, page)
                }
                isScrolling = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        featuredAdapter = (activity as MainActivity).featuredAdapter
        initRecyclerView()
        setupObservers()
        setSearchView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshNews()
        }

        refreshNews()
    }

    private fun setupObservers() {
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            handleApiResponse(response)
        }
        viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
            handleApiResponse(response)
        }
    }

    private fun handleApiResponse(response: Resource<out Any>) {
        when (response) {
            is Resource.Success -> {
                hideProgressBar()
                binding.swipeRefreshLayout.isRefreshing = false
                isLoading = false
                response.data?.let {
                    val newArticles = (it as? com.tech.myapplication.data.model.ApiResponse)?.articles ?: emptyList()
                    val currentArticles = if (page == 1) mutableListOf() else newsAdapter.differ.currentList.toMutableList()
                    currentArticles.addAll(newArticles)
                    newsAdapter.differ.submitList(currentArticles)

                    if (page == 1 && !isSearchMode) {
                        featuredAdapter.differ.submitList(newArticles.toList())
                    }

                    val totalResults = (it as? com.tech.myapplication.data.model.ApiResponse)?.totalResults ?: 0
                    pages = if (totalResults % 20 == 0) {
                        totalResults / 20
                    } else {
                        totalResults / 20 + 1
                    }
                    isLastPage = page >= pages
                }
            }

            is Resource.Error -> {
                hideProgressBar()
                binding.swipeRefreshLayout.isRefreshing = false
                isLoading = false
                response.message?.let {
                    Log.d("TAG", "API Error: $it")
                    Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                }
            }

            is Resource.Loading -> {
                showProgressBar()
                isLoading = true
            }
        }
    }

    private fun refreshNews() {
        isSearchMode = false
        page = 1
        binding.svNews.setQuery("", false)
        binding.svNews.clearFocus()
        viewModel.getNewsHeadlines(country, page)
    }

    private fun initRecyclerView() {
        binding.newsRV.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }

        binding.rvFeatured.apply {
            adapter = featuredAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }


    //setup searchview.
    private fun setSearchView() {
        binding.svNews.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    isSearchMode = true
                    page = 1
                    viewModel.searchNews(country, query, page)
                }
                return false
            }
        })

        binding.svNews.setOnCloseListener {
            refreshNews()
            false
        }
    }
}
