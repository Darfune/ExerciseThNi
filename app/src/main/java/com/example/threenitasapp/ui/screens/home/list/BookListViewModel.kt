package com.example.threenitasapp.ui.screens.home.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Constants
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.data.remote.AndroidDownloader
import com.example.threenitasapp.data.remote.mapper.toBookData
import com.example.threenitasapp.domain.local.usecase.DeleteBookFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.GetAllBookFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.GetBookByIdFromDatabaseUseCase
import com.example.threenitasapp.domain.local.usecase.InsertBookToDatabaseUseCase
import com.example.threenitasapp.domain.remote.model.BookData
import com.example.threenitasapp.domain.remote.usecase.GetBooksFromApiUseCase
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
//    val token: String,
    val getBooksFromApiUseCase: GetBooksFromApiUseCase,
    val getAllBooksFromDatabaseUseCase: GetAllBookFromDatabaseUseCase,
    val insertBooksFromDatabaseUseCase: InsertBookToDatabaseUseCase,
    val getBooksByIdFromDatabaseUseCase: GetBookByIdFromDatabaseUseCase,
    val deleteBookFromDatabaseUseCase: DeleteBookFromDatabaseUseCase,
    val downloader: AndroidDownloader,
) : ViewModel() {

    var _uiRemoteState: MutableStateFlow<RemoteState> = MutableStateFlow(RemoteState())
    val uiRemoteState: StateFlow<RemoteState> = _uiRemoteState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllData("T1amGT21.Idup.298885bf38e99053dca3434eb59c6aa")
        }
    }

    private suspend fun getAllData(token: String) {
        getAllBooksRequest(token)
        getAllBooksForDB()
    }

    fun getAllBooksForDB() = getAllBooksFromDatabaseUseCase()
        .onEach {
            _uiRemoteState.emit(
                _uiRemoteState.value.copy(
                    booksFromDB = Resource.Success(it).data,
                    isLoadingDB = false
                )
            )
            Log.d("BookViewModel", "getAllBooksRequest: ${_uiRemoteState.value}")
        }
        .catch {
            val error = Resource.Error(it.message!!, null)
            _uiRemoteState.emit(
                _uiRemoteState.value.copy(error = error.message!!, isLoadingDB = false)
            )
            Log.d("BookViewModel", "getAllBooksRequest: ${_uiRemoteState.value.error}")
        }
        .launchIn(viewModelScope)


    private suspend fun getAllBooksRequest(token: String) =
        getBooksFromApiUseCase(token).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    var bookHashMap = hashMapOf<String, List<BookData>>()
                    var dates = mutableListOf<String>()
                    response.data!!.forEach { book ->
                        if (!dates.contains(book.dateReleased.substring(0, 7)))
                            dates.add(book.dateReleased.substring(0, 7))
                    }
                    for (date in dates) {
                        var bookList = mutableListOf<BookData>()
                        response.data.forEach { book ->
                            if (book.dateReleased.subSequence(0, 7) == date) {
                                bookList.add(book.toBookData())
                            }
                        }
                        bookHashMap[date] = bookList
                    }
                    _uiRemoteState.emit(
                        _uiRemoteState.value.copy(
                            booksFromApi = bookHashMap,
                            isLoadingApi = false
                        )
                    )
                    Log.d("BookViewModel", "getAllBooksRequest: ${_uiRemoteState.value}")
                }

                is Resource.Error -> {
                    _uiRemoteState.emit(
                        _uiRemoteState.value.copy(
                            error = response.message ?: "An unexpected error occurred",
                            isLoadingApi = false
                        )
                    )

                }

                is Resource.Loading -> _uiRemoteState.value = RemoteState(isLoadingApi = true)
            }
        }.launchIn(viewModelScope)

    fun getDate(date: String): String {
        val year = date.substring(0, 4)
        val month = Constants.monthHashMap[date.substring(5, 7)]
        return "$month $year"
    }

    fun getPDFbyId(id: Int) = viewModelScope.launch {
        getBooksByIdFromDatabaseUseCase(id)
    }

    fun insertPDFtoDatabase(book: BookEntity) = viewModelScope.launch {
        insertBooksFromDatabaseUseCase(book)
    }

    fun startDownload(title: String, url: String): Long =
        downloader.downloadPDF(title, url)





}
