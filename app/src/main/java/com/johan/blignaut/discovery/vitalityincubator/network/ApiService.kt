package com.johan.blignaut.discovery.vitalityincubator.network

import com.johan.blignaut.discovery.vitalityincubator.models.CardBack
import com.johan.blignaut.discovery.vitalityincubator.models.GeoDB
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/geo/places")
    fun getPlaces(): Observable<GeoDB>

    @GET("/geo/places")
    fun getFilteredPlaces(@Query("filterByName") filterByName: String): Observable<GeoDB>

    @GET("/cardbacks")
    fun getCardBacks(): Observable<List<CardBack>>
}