package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpeciesBreed(
    val api_id: Int,
    val breed_name: String?,
    val id: Int,
    val species_name: String?,
    val youth_name: String?
) : Parcelable