package com.example.rxjavaapp.data.model

/**
 * This is a data class with attributes familiar to post json
 *
 */
data class PostModel(val userId: Int, val id: Int, val title: String, val body: String) {
    constructor() : this(0, 0, "", "")
}