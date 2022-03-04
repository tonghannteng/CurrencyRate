package com.tonghann.currencyrate.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tonghann.currencyrate.databinding.ActivityMainBinding
import com.tonghann.currencyrate.model.Rating
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var rateList = ArrayList<String>()
    private var rateHashMap = HashMap<String, Double>()
    private val currencyAdapter = CurrencyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // On Spinner Selected Item
        binding.currencySpinner.onItemSelectedListener = this

        setUpObserver()
        setupUI()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupUI() {
        binding.btnRefrestButton.setOnClickListener {
            viewModel.getAllCurrencyResponse()
            currencyAdapter.notifyDataSetChanged()
            rateList.clear()
        }

        viewModel.errorState.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpObserver() {
        // Get all currency response from ViewModel
        viewModel.getAllCurrencyResponse()

        // Observer currency response
        viewModel.currencyResponse.observe(this) {
            // Spinner
            rateHashMap = it.rates
            for ((key, value) in rateHashMap) {
                rateList.add(key)
            }

            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, rateList.sorted())
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.currencySpinner.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val currencyResultList = ArrayList<Rating>()
        for ((key, value) in rateHashMap) {
            val currentSelectedRate = rateList.sorted()[position]
            val currentSelectedValue = rateHashMap.getValue(currentSelectedRate)
            val calculate = binding.edtAmount.text.toString().toDouble() * (value / currentSelectedValue)
//            Log.d(TAG, "$currentSelected $currentSelectedValue $key $value")
            currencyResultList.add(Rating(key, calculate))

        }

        binding.currencyRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = currencyAdapter
        }

        // Currency RecyclerView
        currencyAdapter.differ.submitList(currencyResultList.sortedBy { it.rateName })
        currencyAdapter.notifyDataSetChanged()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    companion object {
        const val TAG = "MainActivityLog"
    }
}