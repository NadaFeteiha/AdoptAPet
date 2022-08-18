package com.nadafeteiha.adoptapet.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.nadafeteiha.adoptapet.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    abstract val inflate: (LayoutInflater) -> VB
    private lateinit var _binding: VB
    protected val binding: VB
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        start()
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(_binding.root)
        setup()
    }

    abstract fun setup()

    abstract fun start()

}