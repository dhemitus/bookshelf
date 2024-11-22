package com.dhemitus.bookshelf.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhemitus.bookshelf.book.domain.Book
import com.dhemitus.bookshelf.book.domain.BookRepository
import com.dhemitus.bookshelf.core.domain.onError
import com.dhemitus.bookshelf.core.domain.onSuccess
import com.dhemitus.bookshelf.core.presentation.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
): ViewModel() {
    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var observeFavouriteJob: Job? = null

    private var _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if(cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavouriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnBookClick -> {

            }
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is  BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
    private fun observeFavouriteBooks() {
        observeFavouriteJob?.cancel()
        observeFavouriteJob = bookRepository
            .getFavouritesBooks()
            .onEach { favouritebooks ->
                _state.update {
                    it.copy(
                        favoriteBooks = favouritebooks
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchQuery(){
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when{
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        bookRepository
            .searchBooks(query)
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText(),
                        searchResults = emptyList()
                    )
                }
            }
    }
}