package com.nadafeteiha.adoptapet.ui.petspecies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nadafeteiha.adoptapet.base.BaseFragment
import com.nadafeteiha.adoptapet.data.domain.PetSpeciesDetails
import com.nadafeteiha.adoptapet.data.domain.PetSpeciesResponse
import com.nadafeteiha.adoptapet.data.network.NetworkStatus
import com.nadafeteiha.adoptapet.databinding.FragmentPetSpeciesBinding
import com.nadafeteiha.adoptapet.util.showSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PetSpeciesFragment : BaseFragment<FragmentPetSpeciesBinding>() {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetSpeciesBinding =
        FragmentPetSpeciesBinding::inflate
    private lateinit var flow: Flow<NetworkStatus<PetSpeciesResponse>>
    private val petSpeciesAdapter: PetSpeciesAdapter by lazy { PetSpeciesAdapter() }

    override fun setup() {
        flow = flow {
            emit(NetworkStatus.Loading())
            emit(petService.getPetSpecies())
        }.flowOn(Dispatchers.Default)
        flowCollector(flow)
    }


    private fun flowCollector(flow: Flow<NetworkStatus<PetSpeciesResponse>>) {
        lifecycleScope.launch {
            flow.collect { networkResult ->
                checkNetworkResponse(networkResult)
            }
        }
    }

    private fun checkNetworkResponse(status: NetworkStatus<PetSpeciesResponse>) {
        when (status) {
            is NetworkStatus.Loading -> {
                setLoadingUI()
            }
            is NetworkStatus.Success -> {
                status.data?.let { setData(it.petSpeciesDetails) }
            }
            is NetworkStatus.Failure -> {
                requireView().showSnackBar(status.message)
            }
        }
    }

    private fun setData(petSpecies: List<PetSpeciesDetails>) {
        setVisibilityForSuccess()
        binding.petSpeciesRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.petSpeciesRecycler.adapter = petSpeciesAdapter
        petSpeciesAdapter.submitList(petSpecies)
    }

    private fun setVisibilityForSuccess() {
        binding.petSpeciesRecycler.visibility = View.VISIBLE
        binding.loadingPetLottie.visibility = View.GONE
    }

    private fun setLoadingUI() {
        binding.loadingPetLottie.visibility = View.VISIBLE
        binding.petSpeciesRecycler.visibility = View.GONE
    }
}