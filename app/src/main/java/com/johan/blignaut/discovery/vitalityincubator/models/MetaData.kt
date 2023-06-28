package com.johan.blignaut.discovery.vitalityincubator.models

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("currentOffset")
    val currentOffset: Long,
    @SerializedName("totalCount")
    val totalCount: Long
)
