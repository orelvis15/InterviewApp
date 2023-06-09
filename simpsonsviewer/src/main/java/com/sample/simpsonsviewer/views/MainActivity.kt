package com.sample.simpsonsviewer.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.adapters.CharacterAdapter
import com.sample.simpsonsviewer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var adapter: CharacterAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSimpsonCharacterList()
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.search.setOnQueryTextListener(this)

        viewModel.loading.observe(this){
            if (it){
                binding.progress.visibility = View.VISIBLE
            }else{
                binding.progress.visibility = View.INVISIBLE
            }
        }

        viewModel.errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.characterList.observe(this) { item ->
            if (!item.Results.isNullOrEmpty()) {
                adapter = CharacterAdapter(this, item.Results!!) { item ->
                    navController.navigate(
                        R.id.characterDetailsFragment,
                        bundleOf(
                            CharacterDetailsFragment.TITLE to item.title,
                            CharacterDetailsFragment.DESCRIPTION to item.description,
                            CharacterDetailsFragment.IMAGE to item.image
                        )
                    )
                    binding.root.open()
                }
                binding.characterList.adapter = adapter
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }
}