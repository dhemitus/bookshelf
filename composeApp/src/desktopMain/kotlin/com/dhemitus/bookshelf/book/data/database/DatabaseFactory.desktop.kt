package com.dhemitus.bookshelf.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Bookshelf")
            os.contains("mac") -> File(userHome, "Library/Application Support/Bookshelf")
            else -> File(userHome, ".local/share/Bookshelf")
        }

        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, FavouriteBookDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}