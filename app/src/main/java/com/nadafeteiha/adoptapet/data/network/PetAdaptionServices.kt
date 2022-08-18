package com.nadafeteiha.adoptapet.data.network

import com.google.gson.Gson
import com.nadafeteiha.adoptapet.data.domain.AdoptablePet
import com.nadafeteiha.adoptapet.data.domain.AdoptionCenterResponse
import com.nadafeteiha.adoptapet.data.domain.PetDetails
import com.nadafeteiha.adoptapet.util.Constants
import okhttp3.OkHttpClient
import okhttp3.Request

class PetAdaptionServices {
    private val client = OkHttpClient()
    private val builder = Request.Builder()
    private val gson = Gson()

    private inline fun <reified T> callBack(endPoint: String): NetworkStatus<T> {
        val response = client.newCall(builder.url(Constants.BASE_URL + endPoint).build()).execute()
        return if (response.isSuccessful) {
            gson.fromJson(response.body?.string(), T::class.java).run {
                NetworkStatus.Success(this)
            }
        } else {
            NetworkStatus.Failure(response.message)
        }
    }

    fun getAdoptionCenters(): NetworkStatus<AdoptionCenterResponse> {
        return callBack(Constants.CENTER_END_POINT)
    }

    fun getAllAdoptablePets(): NetworkStatus<AdoptablePet> {
        return callBack(Constants.ADOPTABLE_PET_END_POINT)
    }

    fun getPetByID(petID: Int): NetworkStatus<PetDetails> {
        return callBack(Constants.ADOPTABLE_PET_END_POINT + "/$petID")
    }

    fun getSearchQuery(keyword: String): NetworkStatus<AdoptablePet> {
        return callBack(Constants.SEARCH_END_POINT + keyword)
    }
}
