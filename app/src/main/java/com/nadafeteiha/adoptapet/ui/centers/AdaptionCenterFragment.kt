package com.nadafeteiha.adoptapet.ui.centers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.nadafeteiha.adoptapet.base.BaseFragment
import com.nadafeteiha.adoptapet.data.domain.AdoptionCenterResponse
import com.nadafeteiha.adoptapet.data.domain.CenterDetails
import com.nadafeteiha.adoptapet.data.network.NetworkStatus
import com.nadafeteiha.adoptapet.databinding.FragmentAdaptionCenterBinding
import com.nadafeteiha.adoptapet.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AdaptionCenterFragment : BaseFragment<FragmentAdaptionCenterBinding>(),
    CenterAdapter.OnItemClickListener {
    override val inflate: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAdaptionCenterBinding =
        FragmentAdaptionCenterBinding::inflate
    private val adaptionCenterAdapter: CenterAdapter by lazy { CenterAdapter(this) }
    private lateinit var flow: Flow<NetworkStatus<AdoptionCenterResponse>>

    override fun setup() {
        flow = flow {
            emit(NetworkStatus.Loading())
            emit(petService.getAdoptionCenters())
        }.flowOn(Dispatchers.Default)
        flowCollector(flow)
    }

    private fun flowCollector(flow: Flow<NetworkStatus<AdoptionCenterResponse>>) {
        lifecycleScope.launch {
            flow.collect { networkResult ->
                checkNetworkResponse(networkResult)
            }
        }
    }

    private fun checkNetworkResponse(status: NetworkStatus<AdoptionCenterResponse>) {
        when (status) {
            is NetworkStatus.Loading -> {
                setLoadingUI()
            }
            is NetworkStatus.Success -> {
                status.data?.let { setData(it.centers) }
            }
            is NetworkStatus.Failure -> {
               requireActivity().showSnackBar(status.message)
            }
        }
    }

    private fun setData(centers: List<CenterDetails>) {
        setVisibilityForSuccess()
        binding.adaptionCenterRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.adaptionCenterRecycler.adapter = adaptionCenterAdapter
        adaptionCenterAdapter.submitList(centers)
    }

    private fun setVisibilityForSuccess() {
        binding.adaptionCenterRecycler.visibility = View.VISIBLE
        binding.loadingPetLottie.visibility = View.GONE
    }

    private fun setLoadingUI() {
        binding.loadingPetLottie.visibility = View.VISIBLE
        binding.adaptionCenterRecycler.visibility = View.GONE
    }

    override fun onPhoneClick(phoneNumber: String) {
        requireContext().callPhoneCenter(phoneNumber)
    }

    override fun onEmailClick(email: String) {
        requireContext().composeEmail(email)
    }

    override fun onMapClick(center: CenterDetails) {
        requireContext().openMap(center.lat, center.lon, center.name ?: "")
    }

    override fun openAvailablePetClick(center: CenterDetails) {
        Navigation.findNavController(binding.root).navigate(
            AdaptionCenterFragmentDirections.actionAdaptionCenterFragmentToAvailablePetsFragment(
                center
            )
        )
    }

}