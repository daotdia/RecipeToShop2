package com.otero.recipetoshop.domain.dataEstructres

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//Flow común para poder utilizarlo en Swift.
class CommonFLow<T>(private val originFlow: Flow<T>): Flow<T> by originFlow{
    fun collectFlow(
        coroutineScope: CoroutineScope? = null,
        callback: (T) -> Unit,
    ){
        //Necesario para que IOS pueda utilizar indirectamente las corutinas de Android.
        onEach {
            callback(it)
        }.launchIn(coroutineScope ?: CoroutineScope(Dispatchers.Main))
    }
}

//Convierte un flow en commonFlow
fun <T> Flow<T>.asCommonFlow(): CommonFLow<T> = CommonFLow(this)