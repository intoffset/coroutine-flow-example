package dev.cuzira.coroutineflowexample.repository

import dev.cuzira.coroutineflowexample.api.PostApi
import dev.cuzira.coroutineflowexample.model.Comment
import dev.cuzira.coroutineflowexample.model.Future
import dev.cuzira.coroutineflowexample.model.Post
import dev.cuzira.coroutineflowexample.utils.apiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(private val postApi: PostApi) {

    fun getPostsFlow(): Flow<Future<List<Post>>> = apiFlow { postApi.fetchPosts() }

    fun getPostFlow(id: Int): Flow<Future<Post>> = apiFlow { postApi.fetchPost(id) }

    fun getCommentsFlow(id: Int): Flow<Future<List<Comment>>> =
        apiFlow { postApi.fetchComments(id) }
}