package com.kavya.newsreader.viewmodel.data

/**
 * Created by Kavya P S on 16/06/20.
 */
enum class Status(val value: Int) {
    SUCCESS(0),
    LOADING(1),
    ERROR(2)
}

enum class ErrorType(val value: Int) {
    NETWORK_ERROR(0),
    DATA_ERROR(1),
    DB_ERROR(2),
    GENERAL_ERROR(3),
    SERVER_ERROR(4)
}