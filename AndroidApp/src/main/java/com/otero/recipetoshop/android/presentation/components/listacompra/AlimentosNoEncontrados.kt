package com.otero.recipetoshop.android.presentation.components.listacompra

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.RecetaImagen
import com.otero.recipetoshop.android.presentation.components.util.cards.IngredienteCard
import com.otero.recipetoshop.domain.model.despensa.Alimento
import com.otero.recipetoshop.presentationlogic.states.listacompra.ListaCompraState

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun AlimentosNoEncontrados (
    stateListaCompra: MutableState<ListaCompraState>
){
    LazyRow(modifier = Modifier
        .padding(2.dp)){
        items(items = stateListaCompra.value.alimentos_no_encontrados) { item ->
            IngredienteCard(
                nombre = item.nombre,
                cantidad = item.cantidad,
                tipoUnidad = item.tipoUnidad,
                onDelete = {}
            )
        }
    }
}