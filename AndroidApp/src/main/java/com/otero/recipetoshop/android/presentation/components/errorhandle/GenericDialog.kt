package com.otero.recipetoshop.android.presentation.components.errorhandle

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.complementaryColor
import com.otero.recipetoshop.domain.model.NegativeAction
import com.otero.recipetoshop.domain.model.PositiveAction
import org.intellij.lang.annotations.JdkConstants
/*
Este es el componente encargado de generar los dialogos.
Con potenciales acciones positivas y negativas.
 */
@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)?,
    positiveAction: PositiveAction?,
    negativeAction: NegativeAction?,
    title: String,
    description: String? = null,
    onRemoveHeadErrorFromQueue: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
            //Eliminar el mensaje de la cola de errores.
            onRemoveHeadErrorFromQueue()
        },
        text = {
            if(description != null){
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h3
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ){
                if(negativeAction != null) {
                    Button(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = complementaryColor),
                        onClick = {
                            negativeAction.onNegativeAction()
                            onRemoveHeadErrorFromQueue()
                        }
                    ) {
                        Text(
                            text = negativeAction.negativeBtnTxt,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
                if(positiveAction != null) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            positiveAction.onPositiveAction()
                            onRemoveHeadErrorFromQueue()
                        }
                    ) {
                        Text(
                            text = positiveAction.positiveBtnTxt,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    )
}