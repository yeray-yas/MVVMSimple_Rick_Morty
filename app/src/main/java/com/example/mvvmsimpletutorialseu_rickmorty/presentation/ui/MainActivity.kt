package com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmsimpletutorialseu_rickmorty.databinding.ActivityMainBinding
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.adapters.MainAdapter
import com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.characters.list.CharacterListViewModel
import com.example.mvvmsimpletutorialseu_rickmorty.utils.ScreenState
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       viewModel.charactersLiveData.observe(this){ state ->
           processCharactersResponse(state)
       }
    }

    private fun processCharactersResponse(state: ScreenState<List<Character>?>) {
        when (state) {
            is ScreenState.Error -> {
                binding.progressBar.visibility = View.GONE
                val view = binding.progressBar.rootView
                state.message?.let { Snackbar.make(view, state.message, Snackbar.LENGTH_LONG).show() }

            }
            is ScreenState.Loading -> {
               binding.progressBar.visibility = View.VISIBLE
            }

            is ScreenState.Success -> {
                binding.progressBar.visibility = View.GONE
                if (state.data != null) {
                    val adapter = MainAdapter(state.data)
                    val recyclerView = binding.charactersRv
                    recyclerView.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}
