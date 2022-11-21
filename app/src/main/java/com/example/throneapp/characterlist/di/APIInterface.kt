package com.example.throneapp.characterlist.di

import com.example.throneapp.characterlist.model.Characters
import io.reactivex.Observable
import retrofit2.http.GET

interface APIInterface {
    @GET("Characters")
    fun getCharacters() : Observable<List<Characters>>
}
