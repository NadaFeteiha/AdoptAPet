package com.nadafeteiha.adoptapet.ui.petspecies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadafeteiha.adoptapet.data.domain.PetSpeciesDetails
import com.nadafeteiha.adoptapet.databinding.PetSpeciesItemBinding

class PetSpeciesAdapter :
    ListAdapter<PetSpeciesDetails, PetSpeciesAdapter.PetSpeciesViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<PetSpeciesDetails>() {
        override fun areItemsTheSame(
            oldItem: PetSpeciesDetails,
            newItem: PetSpeciesDetails
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PetSpeciesDetails,
            newItem: PetSpeciesDetails
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetSpeciesViewHolder {
        return PetSpeciesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PetSpeciesViewHolder, position: Int) {
        val pet = getItem(position)
        holder.bind(pet)
    }

    class PetSpeciesViewHolder(private var binding: PetSpeciesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(petItem: PetSpeciesDetails) {
            binding.apply {
                pet = petItem
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): PetSpeciesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PetSpeciesItemBinding.inflate(layoutInflater, parent, false)
                return PetSpeciesViewHolder(binding)
            }
        }
    }
}