package com.johan.blignaut.discovery.vitalityincubator.models

import com.google.gson.annotations.SerializedName

data class CardBack(
    @SerializedName("cardBackId")
    val cardBackId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("img")
    val img: String,
    @SerializedName("imgAnimated")
    val imgAnimated: String,
    @SerializedName("sortCategory")
    val sortCategory: Int,
    @SerializedName("sortOrder")
    val sortOrder: Int,
    @SerializedName("locale")
    val locale: String
)
