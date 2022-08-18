package com.nadafeteiha.adoptapet.ui.pet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadafeteiha.adoptapet.data.domain.PetDetails
import com.nadafeteiha.adoptapet.databinding.PetItemBinding

class PetAdapter(private var itemListener: OnItemClickListener) :
    ListAdapter<PetDetails, PetAdapter.PetViewHolder>(DiffCallback) {

    interface OnItemClickListener {
        fun onItemClick(petDetails: PetDetails)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PetDetails>() {
        override fun areItemsTheSame(oldItem: PetDetails, newItem: PetDetails): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PetDetails, newItem: PetDetails): Boolean {
            return oldItem.api_id == newItem.api_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = getItem(position)
        holder.itemView.setOnClickListener {
            itemListener.onItemClick(pet)
        }
        holder.bind(pet)
    }

    class PetViewHolder(private var binding: PetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pet: PetDetails) {
            binding.petDetails = pet
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PetViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PetItemBinding.inflate(layoutInflater, parent, false)
                return PetViewHolder(binding)
            }
        }
    }
}