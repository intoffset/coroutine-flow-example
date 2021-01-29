package dev.cuzira.coroutineflowexample.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.cuzira.coroutineflowexample.model.Future
import dev.cuzira.coroutineflowexample.model.Post
import dev.cuzira.coroutineflowexample.repository.PostRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
    var postId = 0

    val postLiveData = MutableLiveData<Future<Post>>(Future.Proceeding)

    fun refresh() = viewModelScope.launch {
        postRepository.getPostFlow(postId)
            .onEach { postLiveData.value = it }
            .collect()
    }

}