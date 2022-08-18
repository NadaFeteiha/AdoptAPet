package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpeciesBreed(
    val api_id: Int,
    val breed_name: String?,
    val species_name: String?
) : Parcelable