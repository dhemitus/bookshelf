package com.dhemitus.bookshelf.book.data.network

import com.dhemitus.bookshelf.book.data.dto.BookWorkDto
import com.dhemitus.bookshelf.book.data.dto.SearchResponseDto
import com.dhemitus.bookshelf.core.domain.DataError
import com.dhemitus.bookshelf.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetail(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}