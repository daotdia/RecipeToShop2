package com.otero.recipetoshop.android.presentation.components.arquitectura

import android.icu.text.CaseMap
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor


@Composable
fun TopBar(onMenuClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "RecipeToShop")
        },
//        navigationIcon = {
//            IconButton(onClick = {TODO("Pendiente de implementar ir hacia atrás")}) {
//                Icon(Icons.Filled.ArrowBack, "atrás")
//            }
//        },
        backgroundColor = primaryDarkColor,
        contentColor = secondaryLightColor,
        elevation = 10.dp,
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "LeftMenu",
                modifier = Modifier
                    .clickable(onClick = onMenuClicked),
                tint = secondaryLightColor
            )
        }
    ) 
}