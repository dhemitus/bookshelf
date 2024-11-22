package com.dhemitus.bookshelf.book.domain

import com.dhemitus.bookshelf.core.domain.DataError
import com.dhemitus.bookshelf.core.domain.EmptyResult
import com.dhemitus.bookshelf.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>

    fun getFavouritesBooks(): Flow<List<Book>>
    fun isBookFavourite(id: String): Flow<Boolean>
    suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavourites(id: String)
}