package com.otero.recipetoshop.domain.dataEstructres

import com.otero.recipetoshop.domain.model.GenericMessageInfo

/*
Esta clase es muy importante, es la clase necesaria para la comunicación Casos de Uso -> Controladores de Vista.
El controlador se queda en espera de respuesta de los casos de uso en la scope propia. En cuanto
dicho caso de uso devuelve los datos reclamados en forma de DataState; realiza las acciones de presentación necesarias.
 */
class DataState<T>(
    val message: GenericMessageInfo.Builder? = null,
    val data: T? = null,
    val isLoading: Boolean = false,
    val active: Boolean = true
) {
    companion object{
        //Se utiliza para comunicar al controlador que se ha producido un error en back.
        fun<T> error(
            message: GenericMessageInfo.Builder
        ): DataState<T>{
            return DataState(
                message = message,
            )
        }
        //Se utiliza para proporcionar al controlador la respuesta de back. Incluye datos y mensaje.
        fun<T> data(
            message: GenericMessageInfo.Builder? = null,
            data: T? = null,
            active: Boolean = true
        ): DataState<T>{
            return DataState(
                message = message,
                data = data,
                active = active
            )
        }
        //Se utiliza para indicar al controlador que la respuesta está pendiente.
        fun<T> loading() = DataState<T>(isLoading = true)
    }
}