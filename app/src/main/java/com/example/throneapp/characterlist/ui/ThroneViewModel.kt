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

        //To check internat available/else data fetched from db
        if(Util.checkForInternet())
        getPopularMovies()
        else getAllRecords()


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
                    getAllRecords()
                },
                {
                    errorMessage.value = it.toString()
                }
            )

    }

    fun observeCharacterLiveData(): LiveData<List<Characters>> {
        return characterLiveData
    }


    private fun insertRecord(characters: Characters) {
        characterDao.insert(characters)
    }


    private fun getAllRecords() {
        val list = characterDao.getAllRecords()
        characterLiveData.value = list
        if(list.isEmpty())errorMessage.value="No Data Found"
    }


}