package dev.cuzira.coroutineflowexample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.cuzira.coroutineflowexample.model.Future
import dev.cuzira.coroutineflowexample.model.Post
import dev.cuzira.coroutineflowexample.repository.PostRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    private val postRepository: PostRepository,
    @Assisted private val postId: Int,
) : ViewModel() {

    val postLiveData = MutableLiveData<Future<Post>>(Future.Proceeding)

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        postRepository.getPostFlow(postId)
            .onEach { postLiveData.value = it }
            .collect()
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            postId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(postId) as T
            }

        }
    }
   
    @AssistedFactory
    interface Factory {
        fun create(postId: Int): DetailViewModel
    }

}

