package com.yukle.qrcodescanner.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.yukle.qrcodescanner.data.local.helper.DatabaseHelper
import com.yukle.qrcodescanner.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tm.belet.films.presentation.viewmodel.MovieInfoViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 200
    private lateinit var binding: ActivityMainBinding
    private var codeScanner: CodeScanner? = null
    var flashLightStatus: Boolean = false
    private val qrCodeViewModel: MovieInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()
        initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.listBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }
        binding.tryAgainBtn.setOnClickListener {
            codeScanner?.startPreview()
            binding.tryAgainBtn.isVisible = false
        }

        lifecycleScope.launch(Dispatchers.IO) {
            qrCodeViewModel.sendQrCodeValue.collect {
                when {
                    it.isLoading -> {
                        Log.d("TAG_tog tog", "is loading: ")
                    }

                    it.error.isNotEmpty() -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            codeScanner?.releaseResources()
                            Log.d("TAGerrorrr", "initListeners: " + it.error)
                            Toast.makeText(
                                this@MainActivity,
                                "error: ${it.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    it.data != null -> {
                        Log.d("TAGerrorrr", "sssss: " + it.data)
                        lifecycleScope.launch(Dispatchers.Main) {
                            codeScanner?.releaseResources()
                            Toast.makeText(this@MainActivity, "success", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

    private fun initViews() {
        binding.scannerView.isFlashButtonVisible = false

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQrCodeCamera()
            } else {
                checkPermission()
            }
        }
    }

    private fun initQrCodeCamera() {

        codeScanner = CodeScanner(this, binding.scannerView)

        codeScanner?.camera = CodeScanner.CAMERA_BACK
        codeScanner?.formats = CodeScanner.ALL_FORMATS

        codeScanner?.autoFocusMode = AutoFocusMode.SAFE
        codeScanner?.scanMode = ScanMode.SINGLE
        codeScanner?.isAutoFocusEnabled = true
        codeScanner?.isFlashEnabled = false
        codeScanner?.isTouchFocusEnabled = true
        codeScanner?.startPreview()

        codeScanner?.decodeCallback = DecodeCallback {
            DatabaseHelper.addQrCode(it.text)
            qrCodeViewModel.sendQrCode(it.text)
            runOnUiThread {
                codeScanner?.releaseResources()
                binding.tryAgainBtn.isVisible = true
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }
        codeScanner?.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                codeScanner?.releaseResources()
                binding.tryAgainBtn.isVisible = true
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            initQrCodeCamera()
        }
    }

    override fun onStop() {
        super.onStop()
        codeScanner?.releaseResources()
    }

    override fun onStart() {
        super.onStart()

        codeScanner?.startPreview()
    }
}