package com.nadafeteiha.adoptapet.ui.centers.availablepets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.nadafeteiha.adoptapet.base.BaseFragment
import com.nadafeteiha.adoptapet.data.domain.PetDetails
import com.nadafeteiha.adoptapet.data.network.NetworkStatus
import com.nadafeteiha.adoptapet.databinding.FragmentPetBinding
import com.nadafeteiha.adoptapet.ui.pet.PetAdapter
import com.nadafeteiha.adoptapet.ui.pet.PetFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AvailablePetsFragment : BaseFragment<FragmentPetBinding>(),
    PetAdapter.OnItemClickListener {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetBinding =
        FragmentPetBinding::inflate
    private val arguments: AvailablePetsFragmentArgs by navArgs()
    private lateinit var flow: Flow<NetworkStatus<PetDetails>>
    private lateinit var petInCenter: MutableList<PetDetails>
    private val petAdapter: PetAdapter by lazy { PetAdapter(this) }

    override fun setup() {
        val petsInCenter = arguments.center.pets
        flow = flow {
            emit(NetworkStatus.Loading())
            petsInCenter?.let {
                for (pet in petsInCenter) {
                    emit(petService.getPetByID(pet.api_id))
                }
            } ?: emit(NetworkStatus.Failure("No Pets"))
        }.flowOn(Dispatchers.Default)

        flowCollector(flow)
    }

    private fun flowCollector(flow: Flow<NetworkStatus<PetDetails>>) {
        lifecycleScope.launch {
            flow.collect { networkResult ->
                checkNetworkResponse(networkResult)
            }
        }
    }

    private fun checkNetworkResponse(status: NetworkStatus<PetDetails>) {
        when (status) {
            is NetworkStatus.Loading -> {
                setLoading()
            }
            is NetworkStatus.Success -> {
                status.data?.let { pet ->
                    if (this::petInCenter.isInitialized) {
                        petInCenter.add(pet)
                        updateData()
                    } else {
                        setData(pet)
                    }

                }
            }
            is NetworkStatus.Failure -> {
                Toast.makeText(context, status.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setData(pet: PetDetails) {
        setVisibilityForSuccess()
        petInCenter = mutableListOf()
        petInCenter.add(pet)
        binding.petRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.petRecycler.adapter = petAdapter
        petAdapter.submitList(petInCenter)
    }

    private fun setLoading() {
        binding.loadingPetLottie.visibility = View.VISIBLE
        binding.petRecycler.visibility = View.GONE
    }

    private fun setVisibilityForSuccess() {
        binding.petRecycler.visibility = View.VISIBLE
        binding.loadingPetLottie.visibility = View.GONE
    }

    private fun updateData() {
        petAdapter.submitList(petInCenter)
    }

    override fun onItemClick(petDetails: PetDetails) {
        Navigation.findNavController(binding.root)
            .navigate(
                AvailablePetsFragmentDirections.actionAvailablePetsFragmentToPetDetailsFragment(
                    petDetails
                )
            )
    }
}