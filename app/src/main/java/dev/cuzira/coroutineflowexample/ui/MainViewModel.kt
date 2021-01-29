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

class MainViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
    private val TAG = this::class.java.simpleName

    val postsLiveData = MutableLiveData<Future<List<Post>>>(Future.Proceeding)

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        postRepository.getPostsFlow()
            .onEach { postsLiveData.value = it }
            .collect()
    }
}