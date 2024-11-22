package com.dhemitus.bookshelf

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dhemitus.bookshelf.app.App

import com.dhemitus.bookshelf.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "book shelf",
        ) {
            App()
        }
    }
}