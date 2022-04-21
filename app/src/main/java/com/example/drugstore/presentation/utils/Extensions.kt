package com.example.drugstore.presentation.utils

fun Int.toTwoDigitString(): String =
    if (this < 10) "0$this" else "$this"