package com.sample.simpsonsviewer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sample.networking.domain.CharacterDetails
import com.sample.simpsonsviewer.databinding.CharacterTitleItemBinding

class CharacterAdapter(
    private val context: Context,
    private val characters: List<CharacterDetails>,
    private val onClick: (CharacterDetails) -> Unit
) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>(), Filterable {

    private var filteredCharacterList: MutableList<CharacterDetails> = ArrayList(characters)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = CharacterTitleItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        with(holder) {
            with(filteredCharacterList[position]) {
                binding.characterTitle.text = this.title
            }
            itemView.setOnClickListener {
                onClick(filteredCharacterList[position])
            }
        }
    }

    override fun getItemCount() = filteredCharacterList.size

    class CharacterViewHolder(val binding: CharacterTitleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredCharacterList = if (charString.isEmpty()) characters.toMutableList() else {
                    val filteredList = mutableListOf<CharacterDetails>()
                    characters
                        .filter {
                            (it.title.contains(constraint!!)) or
                                    (it.description.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = filteredCharacterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCharacterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<CharacterDetails>
                notifyDataSetChanged()
            }
        }
    }
}