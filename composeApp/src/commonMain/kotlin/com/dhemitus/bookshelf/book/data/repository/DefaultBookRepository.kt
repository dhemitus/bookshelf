package com.dhemitus.bookshelf.book.data.repository

import androidx.sqlite.SQLiteException
import com.dhemitus.bookshelf.book.data.database.FavouriteBookDao
import com.dhemitus.bookshelf.book.data.mappers.toBook
import com.dhemitus.bookshelf.book.data.mappers.toBookEntity
import com.dhemitus.bookshelf.book.data.network.RemoteBookDataSource
import com.dhemitus.bookshelf.book.domain.Book
import com.dhemitus.bookshelf.book.domain.BookRepository
import com.dhemitus.bookshelf.core.domain.DataError
import com.dhemitus.bookshelf.core.domain.EmptyResult
import com.dhemitus.bookshelf.core.domain.Result
import com.dhemitus.bookshelf.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favouriteBookDao: FavouriteBookDao
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>{
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map {
                    it.toBook()
                }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favouriteBookDao.getFavouriteBook(bookId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetail(bookId)
                .map {
                    it.description
                }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavouritesBooks(): Flow<List<Book>> {
        return favouriteBookDao
            .getFavouriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavourite(id: String): Flow<Boolean> {
        return favouriteBookDao
            .getFavouriteBooks()
            .map { bookentities ->
                bookentities.any { it.id == id }
            }
    }

    override suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favouriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavourites(id: String) {
        favouriteBookDao.deleteFavouriteBook(id)
    }
}