package com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmsimpletutorialseu_rickmorty.databinding.ActivityMainBinding
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.adapters.MainAdapter
import com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.characters.list.CharacterListViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.charactersLiveData.collect { pagingData ->
                processCharactersResponse(pagingData)
            }
        }
    }

    private fun processCharactersResponse(pagingData: PagingData<Character>) {
        val adapter = MainAdapter()

        val recyclerView = binding.charactersRv
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            adapter.submitData(pagingData)
        }
    }
}
