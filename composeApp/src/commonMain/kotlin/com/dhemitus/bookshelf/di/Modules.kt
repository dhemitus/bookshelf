package com.dhemitus.bookshelf.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.dhemitus.bookshelf.book.data.database.DatabaseFactory
import com.dhemitus.bookshelf.book.data.database.FavouriteBookDatabase
import com.dhemitus.bookshelf.book.data.network.KtorRemoteBookDataSource
import com.dhemitus.bookshelf.book.data.network.RemoteBookDataSource
import com.dhemitus.bookshelf.book.data.repository.DefaultBookRepository
import com.dhemitus.bookshelf.book.domain.BookRepository
import com.dhemitus.bookshelf.book.presentation.SelectedBookViewModel
import com.dhemitus.bookshelf.book.presentation.book_detail.BookDetailViewModel
import com.dhemitus.bookshelf.book.presentation.book_list.BookListViewModel
import com.dhemitus.bookshelf.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavouriteBookDatabase>().favouriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}