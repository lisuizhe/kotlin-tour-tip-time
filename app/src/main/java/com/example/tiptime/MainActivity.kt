package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTip() {
        val costStr = binding.costOfServiceEditText.text.toString()
        val cost = costStr.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        val tipRate = when (binding.tipOptions.checkedRadioButtonId) {
            binding.tipOptionTwentyPercent.id -> 0.2
            binding.tipOptionEighteenPercent.id -> 0.18
            else -> 0.15
        }
        var tip = cost * tipRate
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}