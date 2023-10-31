package com.yukle.qrcodescanner.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.yukle.qrcodescanner.data.local.helper.DatabaseHelper
import com.yukle.qrcodescanner.databinding.ActivityHistoryBinding
import com.yukle.qrcodescanner.presentation.adapter.AdapterQrCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapterQrCode: AdapterQrCode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        initAdapter()
        getAllQrCodes()
        initListeners()
        setContentView(binding.root)
    }

    private fun initListeners(){
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun initAdapter() {
        adapterQrCode = AdapterQrCode()
        binding.rv.adapter = adapterQrCode
        binding.rv.layoutManager = LinearLayoutManager(this@HistoryActivity)
    }

    private fun getAllQrCodes() {

        lifecycleScope.launch(Dispatchers.IO) {
            DatabaseHelper.getAllQrCodes()?.collect {
                lifecycleScope.launch(Dispatchers.Main) {
                    adapterQrCode.setItems(it)
                }
            }
        }

    }
}