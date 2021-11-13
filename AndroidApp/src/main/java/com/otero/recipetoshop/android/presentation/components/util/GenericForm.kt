package com.otero.recipetoshop.android.presentation.components.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.theme.primaryColor
import com.otero.recipetoshop.android.presentation.theme.secondaryColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.NegativeAction
import com.otero.recipetoshop.domain.model.PositiveAction

@Composable
fun GenericForm(
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)?,
    title: @Composable () -> Unit,
    content: @Composable (() -> Unit)? = null,
    positiveAction: PositiveAction?,
    negativeAction: NegativeAction?,
    positiveEnabled: MutableState<Boolean> = mutableStateOf(true),
    negativeEnabled: MutableState<Boolean> = mutableStateOf(true)
    ) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismiss?.invoke()
        },
        title = title,
        text = {
            if (content != null) {
                content()
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                if (negativeAction != null) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = secondaryColor),
                        onClick = {
                            negativeAction.onNegativeAction()
                        },
                        enabled = negativeEnabled.value
                    ) {
                        Text(
                            text = negativeAction.negativeBtnTxt,
                            color = secondaryLightColor
                        )
                    }
                }
                if (positiveAction != null) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            positiveAction.onPositiveAction()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                        enabled = positiveEnabled.value
                        ) {
                        Text(
                            text = positiveAction.positiveBtnTxt,
                            color = secondaryLightColor
                        )
                    }
                }
            }
        }
    )
}