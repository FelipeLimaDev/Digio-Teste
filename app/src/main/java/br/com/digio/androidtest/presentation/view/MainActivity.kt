package br.com.digio.androidtest.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.digio.androidtest.R
import br.com.digio.androidtest.databinding.ActivityMainBinding
import br.com.digio.androidtest.domain.utils.DataError
import br.com.digio.androidtest.presentation.models.ProductViewState
import br.com.digio.androidtest.presentation.utils.formatColoredText
import br.com.digio.androidtest.presentation.view.adapters.product.ProductAdapter
import br.com.digio.androidtest.presentation.view.adapters.spotlight.SpotlightAdapter
import br.com.digio.androidtest.presentation.viewmodels.ProductsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ProductsViewModel by viewModel()

    private val productAdapter: ProductAdapter by lazy { ProductAdapter() }

    private val spotlightAdapter: SpotlightAdapter by lazy { SpotlightAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            recyMainProducts.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            recyMainProducts.adapter = productAdapter

            recyMainSpotlight.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            recyMainSpotlight.adapter = spotlightAdapter
        }
        setupDigioCashText()
        showContent()
    }

    private fun showContent(visibility: Boolean = false) {
        if (visibility) {
            binding.loadDigioContainer.root.visibility = View.GONE
            binding.body.visibility = View.VISIBLE
        } else {
            binding.loadDigioContainer.root.visibility = View.VISIBLE
            binding.body.visibility = View.GONE
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.products.collect { viewState ->
                    when (viewState) {
                        is ProductViewState.Loading -> showContent(false)
                        is ProductViewState.Error -> showError(viewState)
                        is ProductViewState.Success -> {
                            showContent(true)
                            productAdapter.submitList(viewState.digioProducts.products)
                            spotlightAdapter.submitList(viewState.digioProducts.spotlights)
                        }

                    }
                }
            }
        }
    }

    private fun showError(viewState: ProductViewState.Error) {
        showContent(false)
        val msg = when (viewState.dataError) {
            DataError.Remote.REQUEST_TIMEOUT -> getString(R.string.error_request_timeout)
            DataError.Remote.NO_INTERNET -> getString(R.string.error_no_internet)
            DataError.Remote.UNKNOWN -> getString(R.string.error_unknown_remote)
            DataError.Local.UNKNOWN -> getString(R.string.error_unknown_local)
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    private fun setupDigioCashText() {
        val spannable = formatColoredText(
            baseText = getString(R.string.digio_cash),
            highlightParts = listOf(getString(R.string.digio_cash_highlight)),
            context = this
        )

        binding.txtMainDigioCash.text = spannable
    }
}


