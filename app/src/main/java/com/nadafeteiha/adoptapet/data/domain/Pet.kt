package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val api_id: Int,
    val name: String
) : Parcelable