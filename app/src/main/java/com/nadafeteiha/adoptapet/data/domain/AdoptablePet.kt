package com.nadafeteiha.adoptapet.data.domain

import com.google.gson.annotations.SerializedName

data class AdoptablePet(
    @SerializedName("page")
    val petDetails: List<PetDetails>
)