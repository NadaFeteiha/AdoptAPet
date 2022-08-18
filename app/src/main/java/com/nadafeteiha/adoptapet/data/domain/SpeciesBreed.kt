package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpeciesBreed(
    @SerializedName("breed_name")
    val breedName: String?,
    @SerializedName("species_name")
    val speciesName: String?
) : Parcelable