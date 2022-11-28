package com.example.pbl_sns_project.alluser

data class Followlist(
    val followid: String,
    val followingid: String?,
    val key: Long,
){
    constructor() : this("","",0)
}
