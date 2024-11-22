package com.dhemitus.bookshelf

import androidx.compose.ui.window.ComposeUIViewController
import com.dhemitus.bookshelf.app.App
import com.dhemitus.bookshelf.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }