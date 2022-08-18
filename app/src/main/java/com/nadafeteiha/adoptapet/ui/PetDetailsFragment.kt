package com.nadafeteiha.adoptapet.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.nadafeteiha.adoptapet.base.BaseFragment
import com.nadafeteiha.adoptapet.data.domain.PetDetails
import com.nadafeteiha.adoptapet.databinding.FragmentPetDetailsBinding
import com.nadafeteiha.adoptapet.util.openMap


class PetDetailsFragment : BaseFragment<FragmentPetDetailsBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetDetailsBinding =
        FragmentPetDetailsBinding::inflate
    private val arguments: PetDetailsFragmentArgs by navArgs()

    override fun setup() {
        setData(arguments.pet)
    }

    private fun setData(pet: PetDetails) {
        binding.apply {
            petDetails = pet
            goMapText.setOnClickListener {
                requireContext().openMap(
                    pet.center.latitude,
                    pet.center.longitude,
                    pet.center.centerName ?: ""
                )
            }
        }
    }
}