package com.example.threenitasapp.ui.screens.home.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.local.usecase.DeleteBookFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.GetAllBookFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.GetBookByIdFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.InsertBookToDatabaseUseCase
import com.example.threenitasapp.domain.remote.usecase.GetBooksFromApiUseCase
import com.example.threenitasapp.ui.screens.home.list.state.LocalState
import com.example.threenitasapp.ui.screens.home.list.state.RemoteState

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
    val getBooksFromApiUseCase: GetBooksFromApiUseCase,
    val getAllBooksFromDatabaseUseCase: GetAllBookFromDatabaseUseCase,
    val insertBooksFromDatabaseUseCase: InsertBookToDatabaseUseCase,
    val getBooksByIdFromDatabaseUseCase: GetBookByIdFromDatabaseUseCase,
    val deleteBookFromDatabaseUseCase: DeleteBookFromDatabaseUseCase,
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

    private fun getAllBooksForDB() = getAllBooksFromDatabaseUseCase()
        .onEach {
            _uiLocalState.value = LocalState(storedBooks = Resource.Success(it))
            Log.d("BookListViewModel", "getAllBooksForDB: $it")
        }
        .catch { _uiLocalState.value = LocalState(storedBooks = Resource.Error(it.message!!)) }
        .launchIn(viewModelScope)

    private suspend fun getAllBooksRequest() =
        getBooksFromApiUseCase("T1amGT21.Idup.298885bf38e99053dca3434eb59c6aa").onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _uiRemoteState.value =
                        RemoteState(allBooks = response.data ?: emptyList(), isLoading = false)
                    Log.d("BookListViewModel", "getAllBooksRequest: ${response.data}")
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
