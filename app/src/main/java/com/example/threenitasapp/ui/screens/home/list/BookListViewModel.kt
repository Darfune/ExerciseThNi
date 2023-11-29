package com.example.threenitasapp.ui.screens.home.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.data.remote.AndroidDownloader
import com.example.threenitasapp.data.remote.mapper.toBookData
import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.domain.local.usecase.DeleteBookFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.GetAllBookFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.GetBookByIdFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.InsertBookToDatabaseUseCase
import com.example.threenitasapp.domain.remote.model.BookData
import com.example.threenitasapp.domain.remote.usecase.GetBooksFromApiUseCase
import com.example.threenitasapp.ui.screens.home.list.state.LocalState
import com.example.threenitasapp.ui.screens.home.list.state.RemoteState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
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
    val downloader: AndroidDownloader,
) : ViewModel() {

    var _uiRemoteState: MutableStateFlow<RemoteState> = MutableStateFlow(RemoteState())
    val uiRemoteState: StateFlow<RemoteState> = _uiRemoteState.asStateFlow()

    var _uiLocalState: MutableStateFlow<LocalState> = MutableStateFlow(LocalState(null))
    val uiLocalState: StateFlow<LocalState> = _uiLocalState.asStateFlow()


    @SuppressLint("MutableCollectionMutableState")
    var mapOfBooks = mutableStateOf<HashMap<String, List<BookData>>>(hashMapOf())

    init {
        viewModelScope.launch {
            getAllBooksRequest()
            getAllBooksForDB()
        }
    }

    private fun getAllBooksForDB() = getAllBooksFromDatabaseUseCase()
        .onEach {
            _uiLocalState.value = LocalState(storedBooks = Resource.Success(it))
//            Log.d("BookListViewModel", "getAllBooksForDB: $it")
        }
        .catch { _uiLocalState.value = LocalState(storedBooks = Resource.Error(it.message!!)) }
        .launchIn(viewModelScope)


    private suspend fun getAllBooksRequest() =
        getBooksFromApiUseCase("T1amGT21.Idup.298885bf38e99053dca3434eb59c6aa").onEach { response ->
            when (response) {
                is Resource.Success -> {
                    var bookHashMap = hashMapOf<String, List<BookData>>()
                    var years = mutableListOf<String>()
                    response.data!!.forEach { book ->
                        if (!years.contains(book.dateReleased.substring(0, 4)))
                            years.add(book.dateReleased.substring(0, 4))
                    }
                    for (year in years) {
                        var bookList = mutableListOf<BookData>()
                        response.data.forEach { book ->
                            if (book.dateReleased.subSequence(0, 4) == year) {
                                bookList.add(book.toBookData())
                            }
                        }
                        bookHashMap[year] = bookList
                    }
                    mapOfBooks.value = bookHashMap
                    _uiRemoteState.value = RemoteState(
                        data = response
                    )
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

    suspend fun setupDataToState(response: Resource.Success<List<Book>>) {
        viewModelScope.launch {
            var bookHashMap = hashMapOf<String, List<BookData>>()
            var years = mutableListOf<String>()
            response.data!!.forEach { book ->
                if (!years.contains(book.dateReleased.substring(0, 4)))
                    years.add(book.dateReleased.substring(0, 4))
            }
            for (year in years) {
                var bookList = mutableListOf<BookData>()
                response.data.forEach { book ->
                    if (book.dateReleased.subSequence(0, 4) == year) {
                        bookList.add(book.toBookData())
                    }
                }
                bookHashMap[year] = bookList
            }
            mapOfBooks.value = bookHashMap
        }

    }

    fun getPDFbyId(id: Int) = viewModelScope.launch {
        getBooksByIdFromDatabaseUseCase(id)
    }
    fun insertPDFtoDatabase(book: BookEntity) = viewModelScope.launch {
        insertBooksFromDatabaseUseCase(book)
    }

    fun startDownload(title: String, url: String): Long =
        downloader.downloadPDF(title, url)


    fun observeDownload(id: Long): Flow<Float> = flow {
        if (id != -1L) {
            var progress: Float
            downloader.observeDownloadProgress(id).collectLatest {
                progress = it
                Log.d("BookViewModel", "startAndObserveDownload: $it")
                emit(progress)
            }
        }
    }

}
