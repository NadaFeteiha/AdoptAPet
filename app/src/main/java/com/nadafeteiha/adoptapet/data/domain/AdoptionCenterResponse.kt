package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AdoptionCenterResponse(
    @SerializedName("page")
    val centers: List<CenterDetails>
)

@Parcelize
data class CenterDetails(
    @SerializedName("api_id")
    val centerID: Int,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val centerName: String?,
    @SerializedName("phone")
    val centerPhone: String?,
    @SerializedName("services")
    val centerServices: String?,

    @SerializedName("species_breed")
    val petSpeciesBreed: List<SpeciesBreed>?,
    @SerializedName("pets")
    val petList: List<Pet>?,

    @SerializedName("city")
    val city: String?,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("state")
    val state: String?
) : Parcelable

@Parcelize
data class Pet(
    @SerializedName("api_id")
    val petID: Int
) : Parcelable