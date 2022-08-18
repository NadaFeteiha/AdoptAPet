package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CenterDetails(
    val api_id: Int,
    val city: String?,
    val email: String?,
    val lat: Double,
    val lon: Double,
    val name: String?,
    val pets: List<Pet>?,
    val phone: String?,
    val services: String?,
    val species_breed: List<SpeciesBreed>?,
    val state: String?
) : Parcelable