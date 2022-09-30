package com.example.a5moth3hw

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.a5moth3hw.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var imageAdapter = ImageAdapter(mutableListOf())
    var page = 1
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClicker()
    }

    private fun initClicker() {
        with(binding){
            changePage.setOnClickListener {
                ++page
                doRequesss()
            }
            requestBtn.setOnClickListener {
                doRequesss()
            }
            binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        ++page
                        doRequesss()
                    }
                }
            })
        }
    }

    private fun ActivityMainBinding.doRequesss() {
        App.api.getImages(keyWord = keyWordEd.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(
                    call: Call<PixaModel>,
                    response: Response<PixaModel>
                ) {
                    if (response.code() == 200) {
                        imageAdapter = ImageAdapter(response.body()?.hits!!)
                        binding.recyclerView.adapter = imageAdapter
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    Log.e("ololo", "onFailure:${t.message}")
                }

            })
    }
}