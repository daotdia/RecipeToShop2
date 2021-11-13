package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.otero.recipetoshop.domain.util.TipoUnidad

@Composable
fun NestedDownMenu(
    options: List<String>,
    onClickItem: (String) -> Unit
) {
    val expand = remember { mutableStateOf(false) }
    Box{
        Icon(
            Icons.Filled.MoreVert,
            contentDescription = "NestedDownMenu",
            modifier = Modifier.clickable {
                expand.value = true
            }
        )
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false },
        ) {
            options.forEach { menuitem ->
                DropdownMenuItem(onClick = {
                    onClickItem(menuitem)
                    expand.value = false
                }) {
                    Text(text = menuitem)
                }
            }
        }
    }
}