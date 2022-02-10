package net.alanproject.rickandmorty.ui.activity.search

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.domain.common.KEY_CHAR
import net.alanproject.rickandmorty.databinding.ActivitySearchCharacterBinding
import net.alanproject.rickandmorty.ui.LinearRecyclerViewScrollListener
import net.alanproject.rickandmorty.ui.activity.character.CharacterActivity
import net.alanproject.rickandmorty.ui.adapter.CharacterAdapter

@AndroidEntryPoint
class SearchCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchCharacterBinding
    private val viewModel: SearchCharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_character)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        initListener()
        initAdapter()
    }

    private fun initAdapter() {
        binding.recyclerCharacters.adapter = CharacterAdapter { character ->
            val intent = Intent(this, CharacterActivity::class.java)
                .putExtra(KEY_CHAR, character)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        binding.recyclerCharacters.addOnScrollListener(scrollListener)
    }

    private val scrollListener: RecyclerView.OnScrollListener by lazy {
        LinearRecyclerViewScrollListener(callback = viewModel::searchNextPage)
    }

    private fun initListener() {
        binding.etSearch.addTextChangedListener {
            viewModel.search(it.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

}