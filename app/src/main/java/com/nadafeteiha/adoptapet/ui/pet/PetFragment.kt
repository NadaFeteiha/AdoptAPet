package com.nadafeteiha.adoptapet.ui.pet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nadafeteiha.adoptapet.base.BaseFragment
import com.nadafeteiha.adoptapet.data.domain.AdoptablePet
import com.nadafeteiha.adoptapet.data.domain.PetDetails
import com.nadafeteiha.adoptapet.data.network.NetworkStatus
import com.nadafeteiha.adoptapet.databinding.FragmentPetBinding
import com.nadafeteiha.adoptapet.util.showSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PetFragment : BaseFragment<FragmentPetBinding>(), PetAdapter.OnItemClickListener {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPetBinding =
        FragmentPetBinding::inflate
    private val petAdapter: PetAdapter by lazy { PetAdapter(this) }
    private lateinit var flow: Flow<NetworkStatus<AdoptablePet>>

    override fun setup() {
        flow = flow {
            emit(NetworkStatus.Loading())
            emit(petService.getAllAdoptablePets())
        }.flowOn(Dispatchers.Default)
        flowCollector(flow)
    }

    private fun flowCollector(flow: Flow<NetworkStatus<AdoptablePet>>) {
        lifecycleScope.launch {
            flow.collect { networkResult ->
                checkNetworkResponse(networkResult)
            }
        }
    }

    private fun checkNetworkResponse(status: NetworkStatus<AdoptablePet>) {
        when (status) {
            is NetworkStatus.Loading -> {
                setLoading()
            }
            is NetworkStatus.Success -> {
                status.data?.let { setData(it.petDetails) }
            }
            is NetworkStatus.Failure -> {
                requireView().showSnackBar(status.message)
            }
        }
    }

    private fun setData(petDetails: List<PetDetails>) {
        setVisibilityForSuccess()
        binding.petRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.petRecycler.adapter = petAdapter
        petAdapter.submitList(petDetails)
    }

    private fun setVisibilityForSuccess() {
        binding.petRecycler.visibility = View.VISIBLE
        binding.loadingPetLottie.visibility = View.GONE
    }

    private fun setLoading() {
        binding.loadingPetLottie.visibility = View.VISIBLE
        binding.petRecycler.visibility = View.GONE
    }

    override fun onItemClick(petDetails: PetDetails) {
        Navigation.findNavController(binding.root)
            .navigate(PetFragmentDirections.actionPetFragmentToPetDetailsFragment(petDetails))
    }
}