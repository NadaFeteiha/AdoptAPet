package com.nadafeteiha.adoptapet.data.domain

import com.google.gson.annotations.SerializedName

data class PetSpeciesResponse(
    @SerializedName("page")
    val petSpeciesDetails: List<PetSpeciesDetails>
)