package com.johan.blignaut.discovery.vitalityincubator.models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    val id: Long,
    @SerializedName("wikiDataId")
    val wikiDataId: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("regionCode")
    val regionCode: String,
    @SerializedName("regionWdId")
    val regionWdId: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("population")
    val population: Long,
    @SerializedName("image")
    val image: String
)
