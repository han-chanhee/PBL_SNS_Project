package com.example.pbl_sns_project.home

data class ArticleModel (
    val userId: String,
    val title: String,
    val createdAt: Long,
    val content: String,
    val weather: String,
    val imageUrl: String
) {

    constructor(): this("", "", 0, "", "","")

}