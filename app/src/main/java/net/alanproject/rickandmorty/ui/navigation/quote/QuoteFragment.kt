package net.alanproject.rickandmorty.ui.navigation.quote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import net.alanproject.rickandmorty.R
import net.alanproject.rickandmorty.databinding.FragmentQuoteBinding
import net.alanproject.rickandmorty.ui.navigation.quote.adapter.QuotesPageAdapter
import net.alanproject.domain.model.DomainQuoteModel
import timber.log.Timber

@AndroidEntryPoint
class QuoteFragment : Fragment() {

    private lateinit var binding: FragmentQuoteBinding
    private lateinit var viewModel: QuoteViewModel
    var isDarkMode = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quote, container, false)
        viewModel = ViewModelProvider(this)[QuoteViewModel::class.java]

        binding.fragment = this
        binding.vm = viewModel
        viewModel.quotes.observe(requireActivity()) { quote ->
            Timber.d("initObserving: $quote")
            if (!quote.isNullOrEmpty()) {
                Timber.d("!quote.isNullOrEmpty()")
                displayQuotesPager(quote)
            } else {
                Firebase.crashlytics.recordException(Throwable("fetching quotes failed"))
                Toast.makeText(requireContext(),
                    getString(R.string.network_error_message),
                    Toast.LENGTH_LONG).show()
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkThemeStatus()
        viewModel.fetchQuote()
    }

    private fun checkThemeStatus() {
        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        isDarkMode = pref.getBoolean(getString(R.string.setting_dark_key), true)
    }

    private fun displayQuotesPager(domainQuoteModels: List<DomainQuoteModel>) {
        val adapter = QuotesPageAdapter(domainQuoteModels)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(adapter.itemCount / 2, false)
    }
}