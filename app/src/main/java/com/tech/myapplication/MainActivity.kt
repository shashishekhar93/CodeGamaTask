package com.tech.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tech.myapplication.databinding.ActivityMainBinding
import com.tech.myapplication.presentation.adapter.ChatAdapter
import com.tech.myapplication.presentation.adapter.FeaturedAdapter
import com.tech.myapplication.presentation.adapter.NewsAdapter
import com.tech.myapplication.presentation.viewmodel.ChatViewModel
import com.tech.myapplication.presentation.viewmodel.NewsViewModel
import com.tech.myapplication.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: NewsViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter
    @Inject
    lateinit var featuredAdapter: FeaturedAdapter

    @Inject
    lateinit var chatAdapter: ChatAdapter
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NewsViewModel

    lateinit var chatViewModel: ChatViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnv.setupWithNavController(navController)


    }
}