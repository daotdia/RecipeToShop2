package com.otero.recipetoshop.android.presentation.components.arquitectura

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.otero.recipetoshop.android.R
import com.otero.recipetoshop.android.presentation.navigation.Navegacion
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.primaryLightColor
import com.otero.recipetoshop.presentattion.menus.leftbar.LeftBarItems

@Composable
fun LeftDrawer(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            //Pendiente de poner imagen bonita
            painter = painterResource(id = R.drawable.ic_despensa)
            , contentDescription = "Imagen Aplicacion"
        )
        LeftBarItems.leftItems.forEachIndexed{ index,leftItem ->
            if(index != 0){
                Spacer(modifier = Modifier.height(14.dp))
                Divider(
                    color = primaryColor,
                )
                Spacer(modifier = Modifier.height(14.dp))
            } else {
                Spacer(modifier = Modifier.height(28.dp))
            }

            Text(
                text = leftItem.label,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = {
                        navController.navigate(leftItem.route)
                    }),
                style = MaterialTheme.typography.h6,
                color = primaryDarkColor
            )
        } 
        
    }
}