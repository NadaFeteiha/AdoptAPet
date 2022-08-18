package com.nadafeteiha.adoptapet.data.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetDetails(
    val age: String?,
    val api_id: Int,
    val center: CenterDetails,
    val color: String?,
    val desc: String?,
    val name: String?,
    val pic_url: String?,
    val sex: String?,
    val size_group: String?,
    val species_breed: SpeciesBreed
) : Parcelable
