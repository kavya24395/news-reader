package com.kavya.newsreader.viewmodel.data

/**
 * Created by Kavya P S on 16/06/20.
 */
class Error(
    val errorMessage: String = "General Error",
    val errorType: ErrorType = ErrorType.GENERAL_ERROR
) {}