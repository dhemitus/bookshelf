package com.dhemitus.bookshelf.book.presentation.book_list

import com.dhemitus.bookshelf.book.domain.Book
import com.dhemitus.bookshelf.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
