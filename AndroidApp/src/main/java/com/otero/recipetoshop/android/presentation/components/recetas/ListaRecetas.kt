package com.otero.recipetoshop.android.presentation.components.recetas

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.despensa.FoodCard
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.recetas.Receta
import com.otero.recipetoshop.events.despensa.FoodListEvents
import com.otero.recipetoshop.events.recetas.RecetasListEvents
import com.otero.recipetoshop.presentattion.screens.recetas.RecetasListState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ListaRecetas(
    recetasState: MutableState<RecetasListState>,
    onTriggeEvent: (RecetasListEvents) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    recetasState.value = recetasState.value.copy(onNewReceta = true)
                },
                backgroundColor = primaryDarkColor,
                contentColor = secondaryLightColor,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        val readOnly = remember { mutableStateOf(false) }
        //Crear la lista de items.
        LazyColumn(
            modifier = Modifier
                .offset(y = 48.dp)
                .padding(1.dp),
        ) {
            items(
                items = recetasState.value.recetas,
                { listItem: Receta -> listItem.nombre }
            )
            { item ->
                RevealSwipe(
                    modifier = Modifier
                        .padding(vertical = 5.dp),
                    directions = setOf(
                        RevealDirection.EndToStart
                    ),
                    hiddenContentStart = {
                        Icon(
                            modifier = Modifier.padding(horizontal = 25.dp),
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 25.dp)
//                                    .clickable {onTriggeEvent(RecetasListEvents.onNombreListaChange())}
                            ,
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                ) {
                    RecetaCard(
                        nombre = remember { mutableStateOf(item.nombre) },
                        elevation = 4.dp,
                        readOnly = readOnly,
                        onNombreChange = {
                            onTriggeEvent(RecetasListEvents.onNombreListaChange(it))
                        }
                    )
                }
            }
        }
    }
}