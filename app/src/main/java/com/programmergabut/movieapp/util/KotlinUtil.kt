package com.programmergabut.movieapp.util


fun String.takeCaption(): String =
    if(this.length > 100) this.substring(0, 100) + ".." else this

fun String.setDashIfNullOrEmpty() = if(this.isNullOrEmpty()) "-" else this