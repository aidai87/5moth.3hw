package com.example.a5moth3hw

import android.graphics.pdf.PdfDocument
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaApi {

    @GET("api/")
    fun getImages(
                @Query("key")key:String="25007027-7418deb977c638792f4bfb99f",
                @Query("q")keyWord:String,
                @Query("page") page : Int=1,
                @Query("per_page") perPage: Int = 10
    ):Call<PixaModel>


}