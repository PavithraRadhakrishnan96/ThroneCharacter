package com.example.throneapp.characterlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throneapp.characterlist.Util
import com.example.throneapp.characterlist.di.*
import com.example.throneapp.characterlist.model.Characters
import com.example.throneapp.characterlist.model.CharacterDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ThroneViewModel : ViewModel() {

    @Inject
    lateinit var homeApi: APIInterface

    @Inject
    lateinit var characterDao: CharacterDao

    private lateinit var disposable: Disposable
    private var characterLiveData = MutableLiveData<List<Characters>>()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        val injector: ViewModelInjector = DaggerViewModelInjector.builder()
            .networkModule(NetworkModule())
            .build()

        injector.inject(this)

        if(Util.checkForInternet())
        getPopularMovies()
        else getFilm()


    }

    private fun getPopularMovies() {
        disposable = homeApi.getCharacters().subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    result.forEach {
                        insertRecord(it)
                    }
                    getFilm()

                },
                {
                    errorMessage.value = it.toString()
                }

            )

    }

    fun observeCharacterLiveData(): LiveData<List<Characters>> {
        return characterLiveData
    }


    fun insertRecord(characters: Characters) {
        characterDao.insert(characters)
    }


    fun getFilm() {
        val list = characterDao.getFilm()
        characterLiveData.value = list
    }


}