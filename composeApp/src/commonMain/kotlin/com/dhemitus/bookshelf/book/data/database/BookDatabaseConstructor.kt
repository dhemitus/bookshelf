package com.dhemitus.bookshelf.book.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<FavouriteBookDatabase> {
    override fun initialize(): FavouriteBookDatabase
}