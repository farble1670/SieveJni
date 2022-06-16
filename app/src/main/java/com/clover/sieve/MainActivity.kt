package com.clover.sieve

import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.clover.sieve.databinding.ActivityMainBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import layout.NumberAdapter

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val adapter = NumberAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)

    binding.number.minValue = 2
    binding.number.maxValue = Integer.MAX_VALUE
    binding.number.value = 1024
    binding.number.setOnScrollListener { _, state ->
      if (state == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
        lifecycleScope.launch { updateSieve() }
      }
    }
    val layoutManager = FlexboxLayoutManager(this)
    layoutManager.flexDirection = FlexDirection.ROW
    layoutManager.justifyContent = JustifyContent.FLEX_START
    layoutManager.flexWrap = FlexWrap.WRAP
    binding.grid.layoutManager = layoutManager
    binding.grid.adapter = adapter

    setContentView(binding.root)
  }

  override fun onStart() {
    super.onStart()
    lifecycleScope.launch { updateSieve() }
  }

  private external fun sieve(n: Long): LongArray

  private suspend fun updateSieve() {
    val primes = withContext(Dispatchers.IO) { sieve((binding.number.value + 1).toLong()) }
    adapter.submitList(primes.asList())
  }

  companion object {
    init {
      System.loadLibrary("sieve_jni")
    }
  }
}