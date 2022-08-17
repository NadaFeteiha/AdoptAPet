package com.nadafeteiha.adoptapet.data.domain

import com.google.gson.annotations.SerializedName

data class AdoptionCenterResponse(
    @SerializedName("page")
    val centers: List<CenterDetails>
)