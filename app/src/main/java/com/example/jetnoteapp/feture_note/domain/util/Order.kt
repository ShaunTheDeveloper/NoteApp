package com.example.jetnoteapp.feture_note.domain.util

sealed class OrderType{

    object Ascending : OrderType()
    object Descending : OrderType()

}


sealed class NoteOrder(val orderType: OrderType){

    class ByTitle(orderType: OrderType) : NoteOrder(orderType)
    class ByColor(orderType: OrderType) : NoteOrder(orderType)
    class ByDate(orderType: OrderType) : NoteOrder(orderType)



    fun copy(orderType: OrderType) :NoteOrder{
        return when (this){
            is ByColor -> ByColor(orderType)
            is ByDate -> ByDate(orderType)
            is ByTitle -> ByTitle(orderType)
        }
    }

}



