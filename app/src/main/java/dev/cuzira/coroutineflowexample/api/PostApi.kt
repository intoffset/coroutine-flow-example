package dev.cuzira.coroutineflowexample.api

import dev.cuzira.coroutineflowexample.model.Comment
import dev.cuzira.coroutineflowexample.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun fetchPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun fetchPost(@Path("id") id: Int): Response<Post>

    @GET("posts/{id}/comments")
    suspend fun fetchComments(@Path("id") id: Int): Response<List<Comment>>
}