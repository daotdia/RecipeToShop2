package com.otero.recipetoshop.android.presentation.components.despensa
/*
Este es el componente encargado de generar el diálogo de nuevo alimento.
 */
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.otero.recipetoshop.android.presentation.components.util.GenericForm
import com.otero.recipetoshop.android.presentation.components.util.Keyboard
import com.otero.recipetoshop.android.presentation.components.util.autocomplete.AutoCompleteField
import com.otero.recipetoshop.android.presentation.components.util.keyboardAsState
import com.otero.recipetoshop.android.presentation.theme.primaryDarkColor
import com.otero.recipetoshop.android.presentation.theme.secondaryLightColor
import com.otero.recipetoshop.domain.model.NegativeAction
import com.otero.recipetoshop.domain.model.PositiveAction
import com.otero.recipetoshop.domain.util.TipoUnidad
import com.otero.recipetoshop.presentationlogic.states.despensa.FoodState

@ExperimentalComposeUiApi
@Composable
fun NewAlimentoPopUp(
    onAddAlimento: (String, String, String) -> Unit,
    onNewAlimento: MutableState<Boolean>,
    autocompleteResults: List<String> = listOf(),
    onAutoCompleteChange: (String) -> Unit = {},
    onAutoCompleteClick: () -> Unit = {},
) {
    val newAlimentoState = remember { mutableStateOf(FoodState()) }
    val nombreError = remember { mutableStateOf(false) }
    val cantidadError = remember { mutableStateOf(false) }
    val isAceptable = remember { mutableStateOf(false) }
    val onClickAutoCompleteItem = remember { mutableStateOf(false)}
    val keyBoardState by keyboardAsState()

    //Solo en el caso de que no hayan errores en nombre y cantidad y se haya inicializado se puede dar OK.
    if(
        !nombreError.value &&
        !cantidadError.value &&
        !newAlimentoState.value.nombre.isBlank() &&
        !newAlimentoState.value.nombre.isEmpty() &&
        !newAlimentoState.value.cantidad.isBlank()
    ){
        isAceptable.value = true
    } else {
        isAceptable.value = false
    }

    GenericForm(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
        ,
        onDismiss = {
            onNewAlimento.value = false
        },
        title = {},
        negativeAction = NegativeAction(
            negativeBtnTxt = "Cancelar",
            onNegativeAction = {
                onNewAlimento.value = false
            }
        ),
        positiveAction = PositiveAction(
            positiveBtnTxt = "Añadir",
            onPositiveAction = {
                onNewAlimento.value = false
                onAddAlimento(
                    newAlimentoState.value.nombre,
                    newAlimentoState.value.tipo,
                    newAlimentoState.value.cantidad
                )
            },
        ),
        positiveEnabled = isAceptable,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 24.dp)
                ,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                val keyboardController = LocalSoftwareKeyboardController.current
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 8.dp),
                        textAlign = TextAlign.Center,
                        text = "Registro Nuevo Alimento",
                        style = MaterialTheme.typography.subtitle1,
                        color = primaryDarkColor
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    //Campo para el nombre del alimento.
                    AutoCompleteField(
                        modifier = Modifier,
                        query =
                            if(keyBoardState == Keyboard.Closed){
                                if(onClickAutoCompleteItem.value){
                                    newAlimentoState.value.queryAutoComplete
                                }else{
                                    newAlimentoState.value = newAlimentoState.value.copy("")
                                    onAutoCompleteClick()
                                    newAlimentoState.value.queryAutoComplete
                                }
                            }else{
                                newAlimentoState.value.queryAutoComplete
                            }
                        ,
                        onQueryChanged = {
                            newAlimentoState.value = newAlimentoState.value.copy(queryAutoComplete = it)
                            onAutoCompleteChange(it)
                        },
                        queryLabel = "Nombre",
                        predictions = autocompleteResults,
                        onItemClick = {
                            onClickAutoCompleteItem.value = true
                            newAlimentoState.value = newAlimentoState.value.copy(queryAutoComplete = it)
                            newAlimentoState.value = newAlimentoState.value.copy(nombre = it)
                            onAutoCompleteClick()
                            //En el caso de que el campo de nombre esté vacío lo indico como error.
                            if (
                                newAlimentoState.value.nombre.equals("") ||
                                newAlimentoState.value.nombre.isBlank()
                            ) {
                                nombreError.value = true
                            } else {
                                nombreError.value = false
                            }
                        },
                        onClearClick = {
                            onClickAutoCompleteItem.value = false
                            newAlimentoState.value = newAlimentoState.value.copy(queryAutoComplete = "")
                            newAlimentoState.value = newAlimentoState.value.copy(nombre = "")
                            onAutoCompleteClick()
                        },
                        onDoneActionClick = {
                            onClickAutoCompleteItem.value = false
                            newAlimentoState.value = newAlimentoState.value.copy(queryAutoComplete = "")
                            newAlimentoState.value = newAlimentoState.value.copy(nombre = "")
                            onAutoCompleteClick()
                        },
                        onFocusChanged = {
                            onClickAutoCompleteItem.value = false
                        },
                        isError = nombreError
                    )
//                    OutlinedTextField(
//                        value = newAlimentoState.value.nombre,
//                        onValueChange = {
//                            newAlimentoState.value = newAlimentoState.value.copy(nombre = it)
//                            //En el caso de que el campo de nombre esté vacío lo indico como error.
//                            if(
//                                newAlimentoState.value.nombre.equals("") ||
//                                newAlimentoState.value.nombre.isBlank()
//                            ){
//                                nombreError.value = true
//                            }else{
//                                nombreError.value = false
//                            }},
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            textColor = Color.Black,
//                            backgroundColor = secondaryLightColor,
//                        ),
//                        textStyle = MaterialTheme.typography.body1,
//                        label = { Text(text = "Nombre") },
//                        keyboardOptions = KeyboardOptions(
//                            keyboardType = KeyboardType.Text,
//                            imeAction = ImeAction.Done,
//                        ),
//                        keyboardActions = KeyboardActions(
//                            onDone = {
//                                keyboardController?.hide()
//                            }
//                        ),
//                        isError = nombreError.value
//                    )
                }
                if (autocompleteResults.isEmpty()){
                    Row(
                        modifier = Modifier
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        //Campo para el tipo de unidades del alimento.
                        val expand = remember { mutableStateOf(false) }
                        val iconMenu = if (expand.value) {
                            Icons.Filled.ChevronRight
                        } else {
                            Icons.Filled.ChevronRight
                        }
                        //En este campo hay tanto un primer campo que luego se coinvierte en un desplegable seleccionable con el tipo de alimento de mediad,.
                        OutlinedTextField(
                            modifier = Modifier
                                .clickable { expand.value = !expand.value }
                                .fillMaxWidth(),
                            readOnly = true,
                            value = newAlimentoState.value.tipo,
                            onValueChange = {
                                newAlimentoState.value = newAlimentoState.value.copy(tipo = it)
                            },
                            label = { Text(text = "Unidad Medición") },
                            textStyle = MaterialTheme.typography.body1,
                            trailingIcon = {
                                Icon(
                                    iconMenu,
                                    contentDescription = "menu unidad de medida",
                                    modifier = Modifier.clickable { expand.value = !expand.value }
                                )
                            },
                        )
                        DropdownMenu(
                            modifier = Modifier.fillMaxWidth(),
                            expanded = expand.value,
                            onDismissRequest = { expand.value = false },
                        ) {
                            TipoUnidad.values().forEach { tipo ->
                                DropdownMenuItem(onClick = {
                                    newAlimentoState.value =
                                        newAlimentoState.value.copy(tipo = tipo.name)
                                    expand.value = false
                                }) {
                                    Text(text = tipo.name)
                                }
                            }
                        }
                    }
                    //El campo de cantidad lo pongo en otra linea de la ventana.
                    Row(
                        modifier = Modifier
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //Campo para añadir la cantidad numérica
                        OutlinedTextField(
                            value = newAlimentoState.value.cantidad,
                            onValueChange = {
                                newAlimentoState.value =
                                    newAlimentoState.value.copy(cantidad = it)                    //En el caso de que el campo de cantidad esté vacío lo indico como error.
                                if (
                                    newAlimentoState.value.cantidad.isBlank() ||
                                    newAlimentoState.value.cantidad.toInt() <= 0
                                ) {
                                    cantidadError.value = true
                                } else {
                                    cantidadError.value = false
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                backgroundColor = secondaryLightColor,
                            ),
                            textStyle = MaterialTheme.typography.body1,
                            label = { Text(text = "Cantidad") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            isError = cantidadError.value
                        )
                    }
                }
            }
        }
    )
}

