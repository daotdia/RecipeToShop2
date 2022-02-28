package com.otero.recipetoshop.datasource.network

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.*
import io.ktor.util.network.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
/*
POR IMPLEMENTAR.
 */
class API_Scraping {

    @InternalAPI
    suspend fun scrapQuery(){
        val manager = SelectorManager()
        val socket = aSocket(SelectorManager(Dispatchers.Default)).tcp().connect(NetworkAddress("127.0.0.1", 65432))

        //Abro los canales de entrada y salida.
        val response = socket.openReadChannel()
        val request = socket.openWriteChannel(autoFlush = true)

        //val data_byte = response.()

    }
}