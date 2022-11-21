package com.example.throneapp.characterlist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.throneapp.databinding.ActivityMovieListBinding

class CharacterListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMovieListBinding
    private lateinit var viewModel: ThroneViewModel
    private lateinit var characterListAdapter : CharacterListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[ThroneViewModel::class.java]
        viewModel.observeCharacterLiveData().observe(this, { characterList ->
            characterListAdapter.setCharacterList(characterList)
            binding.pbLoad.visibility= View.INVISIBLE

        })
        viewModel.errorMessage.observe(this, { errorMessage ->
            if (errorMessage != null) showError(errorMessage)
        })


    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show()
        binding.pbLoad.visibility= View.INVISIBLE


    }

    private fun prepareRecyclerView() {
        characterListAdapter = CharacterListAdapter()
        binding.rvCharacter.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = characterListAdapter
        }
    }

}