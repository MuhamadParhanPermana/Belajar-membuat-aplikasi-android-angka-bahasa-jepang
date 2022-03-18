package com.lauwba.angkabahasajepang

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.lauwba.angkabahasajepang.databinding.ActivityLetsPlayBinding
import com.lauwba.angkabahasajepang.databinding.ItemGambarAdapterBinding

class LetsPlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLetsPlayBinding

    //deklrasi variable untuk menampung resources yang akan dipakai
    private val angka = arrayListOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    )

    //deklarasi suara angka
    private val suaraAngka = arrayListOf(
        R.raw.satu, R.raw.dua, R.raw.tiga, R.raw.empat,
        R.raw.lima, R.raw.enam, R.raw.tujuh, R.raw.delapan,
        R.raw.sembilan, R.raw.sepuluh
    )

    //deklarasi gambar angka
    private val gambarAngka = arrayListOf(
        R.drawable.satu, R.drawable.dua, R.drawable.tiga, R.drawable.empat,
        R.drawable.lima, R.drawable.enam, R.drawable.tujuh, R.drawable.delapan,
        R.drawable.sembilan, R.drawable.sepuluh
    )

    //deklarasi adapter untuk halaman
    private lateinit var adapter: PagerAdapter

    //deklarasi media player
    private var mMediaPlayer: MediaPlayer? = null

    //deklarasi variable untuk event ketika di swipe kiri/kanan
    private val onPageListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int, positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
            //ketika si halaman di munculkan/terpilih
            startAudio(position)
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetsPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //kasih nilai awal untuk adapternya
        adapter = AdapterGambar()
        binding.viewPager.adapter = adapter
        //fungsi nya untuk memberika si viewpager agar bisa
        //mendeteksi apakah halaman nya terganti/berganti
        binding.viewPager.addOnPageChangeListener(onPageListener)

        //initial untuk suara dan halamannya
        //halaman awal
        binding.viewPager.currentItem = 0
        startAudio(0)
    }

    override fun onStop() {
        super.onStop()
        destroyMediaPlayer()
    }

    fun destroyMediaPlayer() {
        //check apakah media player is null
        if (mMediaPlayer != null) {
            mMediaPlayer?.reset()
            mMediaPlayer?.release()
        }
        mMediaPlayer = null
    }

    fun startAudio(position: Int) {
        this.destroyMediaPlayer()
        mMediaPlayer = MediaPlayer.create(this@LetsPlayActivity, suaraAngka[position])
        mMediaPlayer?.start()
    }

    private inner class AdapterGambar : PagerAdapter() {
        override fun getCount(): Int {
            return gambarAngka.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as RelativeLayout
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as RelativeLayout)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = ItemGambarAdapterBinding.inflate(
                LayoutInflater.from(container.context),
                container,
                false
            )
            //load gambar angkanya pakai Glide
            Glide.with(this@LetsPlayActivity)
                .load(gambarAngka[position])
                .into(binding.gambarAngka)

            //load gambar untuk replay
            Glide.with(this@LetsPlayActivity)
                .load(R.drawable.audio)
                .into(binding.replay)

            //set angkanya sesuai dengan halaman sekarang
            binding.huruf.text = angka[position]

            //kasih lik untuk ikon replay. Buat puter ulang suaranya
            binding.replay.setOnClickListener {
                this@LetsPlayActivity.startAudio(position)
            }
            (container as ViewPager).addView(binding.root)
            return binding.root
        }

    }
}