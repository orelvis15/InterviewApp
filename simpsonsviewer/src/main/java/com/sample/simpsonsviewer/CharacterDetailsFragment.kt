package com.sample.simpsonsviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)

        Glide.with(requireContext())
            .load(arguments?.getString(IMAGE))
            .placeholder(R.drawable.simpson_loading)
            .error(R.drawable.image_simpson_not_found)
            .into(binding.image)

        binding.title.text = arguments?.getString(TITLE)
        binding.description.text = arguments?.getString(DESCRIPTION)
        return binding.root
    }

    companion object{
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
    }
}