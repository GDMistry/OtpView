package com.gdmistry.otpview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdmistry.otpview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.otpView.setCount(6)
        binding.otpView.setEditTextHint("0")
        binding.otpView.setEditTextSize(43f)
        binding.otpView.setOtpText("232233")
        binding.otpView.setCursorVisible()
    }
}