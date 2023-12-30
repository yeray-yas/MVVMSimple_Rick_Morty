package com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmsimpletutorialseu_rickmorty.databinding.ActivityMainBinding
import com.example.mvvmsimpletutorialseu_rickmorty.model.Character
import com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.adapters.MainAdapter
import com.example.mvvmsimpletutorialseu_rickmorty.presentation.ui.characters.list.CharacterListViewModel
import com.example.mvvmsimpletutorialseu_rickmorty.utils.ViewState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.charactersRv
        val adapter = MainAdapter()

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.charactersLiveData.collect { viewState ->
                when (viewState) {
                    is ViewState.Error -> {
                       binding.progressBar.visibility = View.GONE
                        showError(viewState.exception)
                    }
                    is ViewState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ViewState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        processCharactersResponse(viewState.data, adapter)
                    }
                }
            }
        }
    }

    private fun processCharactersResponse(pagingData: PagingData<Character>, adapter: MainAdapter) {
        lifecycleScope.launch {
            adapter.submitData(pagingData)
        }
    }

    private fun showError(exception: Throwable) {
        // Puedes mostrar un mensaje de error al usuario, enviar informes de errores, etc.
        Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
    }
}

