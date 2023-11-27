package com.example.threenitasapp.ui.screens.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.usecases.local.DeleteBookUseCase
import com.example.threenitasapp.domain.usecases.local.GetAllBooksUseCase
import com.example.threenitasapp.domain.usecases.local.GetBooksByIdUseCase
import com.example.threenitasapp.domain.usecases.local.InsertBooksUseCase
import com.example.threenitasapp.domain.usecases.local.UpdateBooksUseCase
import com.example.threenitasapp.domain.usecases.remote.GetBooksUseCase
import com.example.threenitasapp.ui.screens.list.state.LocalState
import com.example.threenitasapp.ui.screens.list.state.RemoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    val getBooksUseCase: GetBooksUseCase,
    val getAllBooksUseCase: GetAllBooksUseCase,
    val getBooksByIdUseCase: GetBooksByIdUseCase,
    val insertBooksUseCase: InsertBooksUseCase,
    val updateBooksUseCase: UpdateBooksUseCase,
    val deleteBookUseCase: DeleteBookUseCase,
) : ViewModel() {

    var _uiRemoteState: MutableState<RemoteState> = mutableStateOf(RemoteState())
    val uiRemoteState: State<RemoteState> = _uiRemoteState

    var _uiLocalState: MutableStateFlow<LocalState> = MutableStateFlow(LocalState(null))
    val uiLocalState: StateFlow<LocalState> = _uiLocalState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllBooksRequest()
            getAllBooksForDB()
        }
    }

    private fun getAllBooksForDB() = getAllBooksUseCase()
        .onEach {
            _uiLocalState.value = LocalState(storedBooks = Resource.Success(it))
        }
        .catch { _uiLocalState.value = LocalState(storedBooks = Resource.Error(it.message!!)) }
        .launchIn(viewModelScope)

    private suspend fun getAllBooksRequest() =
        getBooksUseCase("T1amGT21.Idup.298885bf38e99053dca3434eb59c6aa").onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _uiRemoteState.value =
                        RemoteState(allBooks = response.data ?: emptyList(), isLoading = false)
                }

                is Resource.Error -> {
                    _uiRemoteState.value = RemoteState(
                        error = response.message ?: "An unexpected error occurred",
                        isLoading = false
                    )

                }

                is Resource.Loading -> _uiRemoteState.value = RemoteState(isLoading = true)
            }
        }.launchIn(viewModelScope)
}
