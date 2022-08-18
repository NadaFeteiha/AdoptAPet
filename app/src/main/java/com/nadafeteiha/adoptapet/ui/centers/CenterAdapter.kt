package com.nadafeteiha.adoptapet.ui.centers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadafeteiha.adoptapet.data.domain.CenterDetails
import com.nadafeteiha.adoptapet.databinding.AdaptionCenterItemBinding

class CenterAdapter(private var itemListener: OnItemClickListener) :
    ListAdapter<CenterDetails, CenterAdapter.CenterViewHolder>(DiffCallback) {

    interface OnItemClickListener {
        fun onPhoneClick(phoneNumber: String)
        fun onEmailClick(email: String)
        fun onMapClick(center: CenterDetails)
        fun openAvailablePetClick(center: CenterDetails)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CenterDetails>() {
        override fun areItemsTheSame(oldItem: CenterDetails, newItem: CenterDetails): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CenterDetails, newItem: CenterDetails): Boolean {
            return oldItem.centerID == newItem.centerID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterViewHolder {
        return CenterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CenterViewHolder, position: Int) {
        val center = getItem(position)
        holder.bind(center, itemListener)
    }

    class CenterViewHolder(private var binding: AdaptionCenterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(center: CenterDetails, itemListener: OnItemClickListener) {
            binding.apply {
                adaptionCenter = center
                setListeners(center, itemListener)
                executePendingBindings()
            }
        }

        private fun setListeners(center: CenterDetails, itemListener: OnItemClickListener) {
            binding.apply {
                phoneText.setOnClickListener {
                    center.centerPhone?.let { phoneNumber ->
                        itemListener.onPhoneClick(phoneNumber)
                    }
                }
                emailText.setOnClickListener {
                    center.email?.let { email ->
                        itemListener.onEmailClick(email)
                    }
                }

                locationText.setOnClickListener {
                    itemListener.onMapClick(center)
                }

                availablePetAction.setOnClickListener {
                    itemListener.openAvailablePetClick(center)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): CenterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdaptionCenterItemBinding.inflate(layoutInflater, parent, false)
                return CenterViewHolder(binding)
            }
        }
    }
}