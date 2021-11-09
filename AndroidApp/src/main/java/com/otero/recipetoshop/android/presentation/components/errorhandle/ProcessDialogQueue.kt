package com.otero.recipetoshop.android.presentation.components.errorhandle

import androidx.compose.runtime.Composable
import com.otero.recipetoshop.domain.model.GenericMessageInfo
import com.otero.recipetoshop.domain.util.Queue

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericMessageInfo>?,
    onRemoveHeadMessageFromQueue: () -> Unit
){
    dialogQueue?.peek()?.let{ dialogInfo ->
        GenericDialog(
            title = dialogInfo.title,
            description = dialogInfo.description,
            onRemoveHeadErrorFromQueue = onRemoveHeadMessageFromQueue,
            onDismiss = dialogInfo.onDismiss,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}