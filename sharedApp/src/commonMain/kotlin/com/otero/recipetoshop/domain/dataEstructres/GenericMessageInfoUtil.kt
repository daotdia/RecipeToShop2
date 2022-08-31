package com.otero.recipetoshop.domain.dataEstructres

import com.otero.recipetoshop.domain.model.GenericMessageInfo

//Como no se pueden usar funciones de extensión en el módulo share de KMM
//Es necesario crear una clase aparte con las utilidades necesarias.
class GenericMessageInfoUtil(){
    fun doesMessageAlreadyExistInQueue(
        queue: Queue<GenericMessageInfo>,
        messageInfo: GenericMessageInfo
    ): Boolean{
        for (item in queue.items){
            if(item.id == messageInfo.id){
                return true
            }
        }
        return false
    }
}