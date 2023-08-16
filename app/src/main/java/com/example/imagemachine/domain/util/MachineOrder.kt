package com.example.imagemachine.domain.util

sealed class MachineOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): MachineOrder(orderType)
    class Type(orderType: OrderType): MachineOrder(orderType)

    fun copy(orderType: OrderType): MachineOrder {
        return when(this) {
            is Name -> Name(orderType)
            is Type -> Type(orderType)
        }
    }
}