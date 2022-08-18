package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AdoptablePetResponse(
    @SerializedName("page")
    val petDetails: List<PetDetails>
)
@Parcelize
data class PetDetails(
    @SerializedName("api_id")
    val petID: Int,

    @SerializedName("name")
    val petName: String?,
    @SerializedName("age")
    val ageLevel: String?,
    @SerializedName("color")
    val petColor: String?,
    @SerializedName("desc")
    val petDescription: String?,
    @SerializedName("pic_url")
    val petPictureURL: String?,
    @SerializedName("sex")
    val petSex: String?,
    @SerializedName("size_group")
    val prtSizeGroup: String?,

    @SerializedName("species_breed")
    val petSpeciesBreed: SpeciesBreed,
    @SerializedName("center")
    val center: CenterDetails
) : Parcelable
