package com.dhemitus.bookshelf.book.presentation.book_detail

import com.dhemitus.bookshelf.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavouriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}