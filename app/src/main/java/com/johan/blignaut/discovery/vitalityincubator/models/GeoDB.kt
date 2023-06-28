package com.johan.blignaut.discovery.vitalityincubator.models

import com.google.gson.annotations.SerializedName

data class GeoDB(
    @SerializedName("data")
    val data: List<City>,
    @SerializedName("links")
    val links: List<Link>,
    @SerializedName("metadata")
    val metadata: MetaData
)
