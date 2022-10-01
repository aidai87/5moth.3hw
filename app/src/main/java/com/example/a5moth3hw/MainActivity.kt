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
    var imageAdapter = ImageAdapter(arrayListOf())
    var page = 1
    var num = 30
    var perPage = 30
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
                        perPage += num
                        doRequesss()
                    }
                }
            })
        }
    }

    private fun ActivityMainBinding.doRequesss() {
        App.api.getImages(keyWord = keyWordEd.text.toString(), page = page, perPage = perPage)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(
                    call: Call<PixaModel>,
                    response: Response<PixaModel>
                ) {
                    if (response.code() == 200) {
                        val list = arrayListOf<ImageModel>()
                        response.body()?.hits?.forEach{
                       imageAdapter.addImage(it)

                        }
                        binding.recyclerView.adapter = imageAdapter
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    Log.e("ololo", "onFailure:${t.message}")
                }

            })
    }
}