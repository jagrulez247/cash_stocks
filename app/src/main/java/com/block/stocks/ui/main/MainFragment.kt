package com.block.stocks.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.block.stocks.databinding.FragmentMainBinding
import com.block.stocks.ui.main.adaper.CashStockAdapter
import com.block.stocks.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var stocksAdapter: CashStockAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(view)
        observe(viewModel.uiState()) { updateUi(it) }
        viewModel.onViewCreated()
    }

    override fun onDestroyView() {
        stocksAdapter.cleanUp()
        super.onDestroyView()
    }

    private fun updateUi(state: MainUiStates) {
        when (state) {
            MainUiStates.HideProgress -> {
                binding.cashStocksProgressBar.visibility = GONE
                binding.cashStocksNoData.visibility = GONE
                binding.cashStocksRecyclerView.visibility = VISIBLE
            }
            MainUiStates.ShowProgress -> {
                binding.cashStocksProgressBar.visibility = VISIBLE
                binding.cashStocksNoData.visibility = GONE
                binding.cashStocksRecyclerView.visibility = GONE
            }
            is MainUiStates.UpdateStocks -> {
                binding.cashStocksNoData.visibility = GONE
                binding.cashStocksRecyclerView.visibility = VISIBLE
                stocksAdapter.updateStocks(state.stocks)
            }
            is MainUiStates.NoData -> {
                binding.cashStocksNoData.visibility = VISIBLE
                binding.cashStocksRecyclerView.visibility = GONE
            }
        }
    }

    private fun setRecyclerView(view: View) {
        binding.cashStocksRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = CashStockAdapter().apply { stocksAdapter = this }
        }
    }
}