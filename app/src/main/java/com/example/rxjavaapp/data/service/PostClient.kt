package com.example.rxjavaapp.data.service

import com.example.rxjavaapp.data.model.PostModel
import com.example.rxjavaapp.data.remote.ApiConstant
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class uses to declare one instance of retrofit builder to prevent the redundancy
 */
object PostClient {

    val baseURL: String = ApiConstant.baseURL
    val apiInterface: PostInterfaceAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiInterface = retrofit.create(PostInterfaceAPI::class.java)
    }

    /**
     * Call all methods on api interface
     */
    fun getPosts(): Observable<List<PostModel>> {

        return apiInterface.getPost()
    }

}