package com.otero.recipetoshop.android.presentation.components.listacompra

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.otero.recipetoshop.events.listacompra.ListaCompraEvents
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState
import kotlin.reflect.KFunction1

@Composable
fun ListaCompra(
    navController: NavController,
    listaCompraState: MutableState<ListaCompraState>,
    onTriggeEvent: (ListaCompraEvents) -> Unit,
){
    LazyColumn(Modifier.fillMaxSize()){
        items(items = listaCompraState.value.listaProductos){ item ->
            Text(text = item.nombre)
        }
    }
}