package com.example.pbl_sns_project.alluser

data class followlist(
    val followid: String,
    val followingid: String?,
    val key: Long,
){
    constructor() : this("","",0)
}
