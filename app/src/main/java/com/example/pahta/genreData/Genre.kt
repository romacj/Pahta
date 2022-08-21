package com.example.pahta.genreData

data class Genre(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)