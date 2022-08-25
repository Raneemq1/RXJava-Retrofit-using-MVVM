package com.example.rxjavaapp.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjavaapp.data.model.PostModel
import com.example.rxjavaapp.data.service.PostClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PostViewModel : ViewModel() {

    var data: MutableLiveData<List<PostModel>> = MutableLiveData()

    /**
     * This method uses to get the data from api interface as observable list
     * Observable must be in a background thread so scheduler.io() used
     * Observer must connect to ui thread so it must returns back to main thread
     */
    @SuppressLint("CheckResult")
    fun getData() {
        PostClient.getPosts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { data.value = it }


    }
}