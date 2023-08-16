package com.example.imagemachine.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}