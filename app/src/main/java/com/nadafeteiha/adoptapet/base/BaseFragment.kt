package com.nadafeteiha.adoptapet.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.nadafeteiha.adoptapet.data.network.PetAdaptionServices


abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> VB
    private lateinit var _binding: VB
    protected val binding: VB
        get() = _binding

    val petService: PetAdaptionServices by lazy { PetAdaptionServices() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate(inflater, container, false)
        setup()
        return _binding.root
    }

    abstract fun setup()

}