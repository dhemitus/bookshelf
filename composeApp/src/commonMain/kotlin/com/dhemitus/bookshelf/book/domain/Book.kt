package com.dhemitus.bookshelf.book.domain

data class Book(
    val id: String,
    val title: String,
    val imageUrl: String,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishYear: String?,
    val avarageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)
