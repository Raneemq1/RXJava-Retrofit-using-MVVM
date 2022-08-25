package com.example.rxjavaapp.data.service

import com.example.rxjavaapp.data.model.PostModel
import io.reactivex.Observable
import retrofit2.http.GET

interface PostInterfaceAPI {

    /**
     * REST API with GET method to get all posts from placeholder website
     */
    @GET("posts")
    fun getPost(): Observable<List<PostModel>>
}