package com.nadafeteiha.adoptapet.ui

import android.text.Editable
import android.text.TextWatcher
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
import com.nadafeteiha.adoptapet.databinding.FragmentSearchBinding
import com.nadafeteiha.adoptapet.ui.pet.PetAdapter
import com.nadafeteiha.adoptapet.util.Constants
import com.nadafeteiha.adoptapet.util.showSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(), PetAdapter.OnItemClickListener {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate
    private val petAdapter: PetAdapter by lazy { PetAdapter(this) }
    private lateinit var flow: Flow<NetworkStatus<AdoptablePet>>

    override fun setup() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(keyword: Editable) {
                search(keyword.toString())
            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })
    }

    private fun search(keyword: String) {
        flow = flow {
            emit(NetworkStatus.Loading())
            emit(petService.getSearchQuery(keyword))
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
                status.data?.let {
                    setData(it.petDetails)
                } ?: noDataFromSearch()
            }
            is NetworkStatus.Failure -> {
                requireActivity().showSnackBar(status.message)
            }
        }
    }

    private fun setData(petDetails: List<PetDetails>) {
        setVisibilityForSuccess()
        petAdapter.submitList(petDetails)
        binding.apply {
            petRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
            petRecycler.adapter = petAdapter
        }
    }

    private fun noDataFromSearch() {
        binding.apply {
            petRecycler.visibility = View.GONE
            loadingPetLottie.visibility = View.VISIBLE
            loadingPetLottie.setAnimation(Constants.ANIMATION_NOT_FOUND)
        }
    }

    private fun setVisibilityForSuccess() {
        binding.apply {
            petRecycler.visibility = View.VISIBLE
            loadingPetLottie.visibility = View.GONE
        }
    }

    private fun setLoading() {
        binding.apply {
            loadingPetLottie.visibility = View.VISIBLE
            petRecycler.visibility = View.GONE
        }
    }

    override fun onItemClick(petDetails: PetDetails) {
        Navigation.findNavController(binding.root)
            .navigate(SearchFragmentDirections.actionSearchFragmentToPetDetailsFragment(petDetails))
    }
}