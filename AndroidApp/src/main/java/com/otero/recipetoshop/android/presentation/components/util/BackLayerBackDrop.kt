package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.navigation.RutasNavegacion
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.primaryLightColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor

@Composable
fun BackLayerBackDrop(
    menuItems: List<MenuItemBackLayer>,
    onMenuItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        menuItems.forEach { menuItem ->
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onMenuItemClick(menuItem.ruta)
                            menuItem.foccused.value = true
                        }
                    ),
                text = menuItem.nombre,
                color = if (menuItem.foccused.value) {
                    primaryColor
                } else {
                    primaryLightColor
                }
                ,
                style = TextStyle(
                    color = secondaryLightColor
                ),
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

data class MenuItemBackLayer(
    val nombre: String,
    val foccused: MutableState<Boolean>,
    val ruta: String
)