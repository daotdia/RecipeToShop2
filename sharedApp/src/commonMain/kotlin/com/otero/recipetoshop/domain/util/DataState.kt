package com.otero.recipetoshop.domain.util

import com.otero.recipetoshop.domain.model.GenericMessageInfo

class DataState<T>(
    val message: GenericMessageInfo.Builder? = null,
    val data: T? = null,
    val isLoading: Boolean = false,
    val active: Boolean = true
) {
    companion object{
        fun<T> error(
            message: GenericMessageInfo.Builder
        ): DataState<T>{
            return DataState(
                message = message,
            )
        }

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

        fun<T> loading() = DataState<T>(isLoading = true)
    }
}