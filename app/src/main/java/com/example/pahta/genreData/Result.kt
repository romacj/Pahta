package com.example.pahta.genreData

data class Result(
    val games: List<Game>,
    val games_count: Int,
    val id: Int,
    val image_background: String,
    val name: String,
    val slug: String,
    var flag: Boolean = false
)