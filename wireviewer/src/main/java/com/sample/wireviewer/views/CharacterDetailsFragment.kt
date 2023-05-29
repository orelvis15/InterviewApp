package com.sample.wireviewer.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sample.wireviewer.R
import com.sample.wireviewer.databinding.FragmentCharacterDetailsBinding

class CharacterDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)

        if (arguments?.isEmpty == false){
            Glide.with(requireContext())
                .load(arguments?.getString(IMAGE))
                .placeholder(R.drawable.generic_loading)
                .error(R.drawable.image_not_available)
                .into(binding.image)

            binding.title.text = arguments?.getString(TITLE)
            binding.description.text = arguments?.getString(DESCRIPTION)
        }else{
            binding.description.text = getText(R.string.character_unselected)
        }

        return binding.root
    }

    companion object{
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE = "image"
    }
}