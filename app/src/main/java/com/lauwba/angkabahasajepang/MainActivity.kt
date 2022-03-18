package com.lauwba.angkabahasajepang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lauwba.angkabahasajepang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.letsPlay.setOnClickListener {
            val letsPlay = Intent(this@MainActivity, LetsPlayActivity::class.java)
            startActivity(letsPlay)
        }
        binding.tentang.setOnClickListener {
            val tentang = Intent(this@MainActivity, TentangActivity::class.java)
            startActivity(tentang)
        }
    }
}