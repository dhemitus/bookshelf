package com.dhemitus.bookshelf.book.presentation.book_detail

import com.dhemitus.bookshelf.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavourite: Boolean = false,
    val book: Book? = null
)
