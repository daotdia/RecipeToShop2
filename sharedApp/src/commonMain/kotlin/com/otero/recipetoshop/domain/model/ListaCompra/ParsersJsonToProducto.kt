package com.otero.recipetoshop.domain.model.ListaCompra

import com.otero.recipetoshop.domain.util.FilterEnum
import com.otero.recipetoshop.domain.util.SupermercadosEnum
import com.otero.recipetoshop.domain.util.TipoUnidad
import kotlin.math.roundToInt

class ParsersJsonToProducto {

    companion object {
        fun parseJsonPrecioPesoToProductoPrecioPeso(elemento: Productos.Producto): Float?{
            when(elemento.supermercado){
                SupermercadosEnum.CARREFOUR ->
                    return parseJsonPrecioPesoToProductoPrecioPesoCarrefour(elemento)
                SupermercadosEnum.DIA ->
                    return parseJsonPrecioPesoToProductoPrecioPesoDia(elemento)
                SupermercadosEnum.MERCADONA ->
                    return parseJsonPrecioPesoToProductoPrecioPesoMercadona(elemento)
            }
        }

        fun parsePesoJsonToProductoJson(elemento: Productos.Producto):  Map<String, Any>?{
            when(elemento.supermercado){
                SupermercadosEnum.CARREFOUR ->
                    return parseJsonPesoToProductoPesoCarrefour(elemento)
                SupermercadosEnum.DIA ->
                    return parseJsonPesoToProductoPesoDia(elemento)
                SupermercadosEnum.MERCADONA ->
                    return parseJsonPesoToProductoPesoMercadona(elemento)
            }
        }

        private fun parseJsonPrecioPesoToProductoPrecioPesoCarrefour(elemento: Productos.Producto): Float? {
            if(!elemento.precio_peso.isEmpty()){
                println("Precio_peso Carrefour: " + elemento.precio_peso)
                return elemento.precio_peso
                    .replace(',', '.')
                    .filter { it.isDigit() || it.equals('.') }
                    .toFloat()
            }
            return null
        }

        private fun parseJsonPrecioPesoToProductoPrecioPesoDia(elemento: Productos.Producto): Float? {
            if(!elemento.precio_peso.isEmpty()){
                println("Precio_peso Dia: " + elemento.precio_peso)
                return elemento.precio_peso
                    //Para eliminar el punto dle final.
                    .filter { !it.equals('.') }
                    .replace(',', '.')
                    .filter { it.isDigit() || it.equals('.') }
                    .toFloat()
            }
            return null
        }

        private fun parseJsonPrecioPesoToProductoPrecioPesoMercadona(elemento: Productos.Producto): Float {
            if (elemento.tipoUnidad != null){
                //Ya está calculado el peso en ml, gramos o unidades.
                when(elemento.tipoUnidad){
                    //Divido el precio del producto entre su peso en Kg, L o Ud.
                    TipoUnidad.GRAMOS ->
                        return (elemento.precio_numero / (elemento.peso / 1000))
                    TipoUnidad.ML ->
                        return (elemento.precio_numero / (elemento.peso / 1000))
                    TipoUnidad.UNIDADES ->
                        return elemento.precio_numero
                    else -> {
                        return elemento.precio_numero
                    }
                }
            }
            return elemento.precio_numero
        }

        private fun parseJsonPesoToProductoPesoCarrefour(elemento: Productos.Producto): Map<String, Any>? {
            val nombre = elemento.nombre
            //Obtengo la primera aparición de un número seguido de todos los carácteres restantes.
            val pattern = Regex("[0-9]+[\\w\\s\\W]*")
            val match = pattern.find(nombre)
            if(match != null) {
                println("El pattern general encontrado es: " + match.value)
                return parseCantidadBrutaCarrefour(match.value)
            }
            else
                return null
        }

        private fun parseCantidadBrutaCarrefour(nombreUnidades: String):  Map<String, Any>? {
            val cantidad_producto: HashMap<String, Any>?
            var tipo_unidad: String? = null
            //Obtengo el tipo de unidad
            val pattern_tipoUnidad = Regex("piezas|pieza|tarrinas|tarrina|bandejas|bandeja|latas|lata|sticks|stick|botellas|botella|unidades|unidad|bote|botes|tarros|tarro|sobre|sobres|envases|envase|paquete|paquetes|bolsas|bolsa|briks|brik|mg|g|dg|cg|kg|ml|cl|dl|l|lt|ud", RegexOption.IGNORE_CASE)
            // \\s** = cualquier número de espacioes en blanco
            val pattern_cantidad = Regex("[0-9]+,?[0-9]*\\s*" + "(" + pattern_tipoUnidad + ")")
            val sequencia_unidad = pattern_tipoUnidad.findAll(nombreUnidades)
            val valor_multiplicador = pattern_cantidad.findAll(nombreUnidades)
            println("Las tipos unidades obtenidas" + " del nombre " + nombreUnidades +  "son: " )
            sequencia_unidad.toList().forEach { println("Sequencia: " + it.value) }
            println("Las cantidades obtenidas son: ")
            valor_multiplicador.toList().forEach { println("valor-Multiplicador: " + it.value)}
            var nombre_unidad: String? = null
            var multiplicador_unidad: Float = 1f
            var cantidad_unidad_bruta: Float? = null
            //En el caso de que se ahay encontrado al menos una ocurrencia se puede sacar las unidades.
            if(sequencia_unidad.toList().size > 0){
                nombre_unidad = sequencia_unidad.last().value
                //En el caso de que haya más de una ocurrencia, se obtiene el multiplicador de la primera.
                if(valor_multiplicador.toList().size > 1){
                    println("El valor del multiplicador es: " + valor_multiplicador.first().value)
                    multiplicador_unidad = valor_multiplicador
                        .first()
                        .value
                        .replace(',', '.')
                        .filter { it.isDigit() || it.equals('.') }
                        .toFloat()
                }
                //En el caso de que haya al menos una ocurrencia se obtiene la cantidad
                //de la multiplicaciń del multiplicador por la última ocurrencia.
                if(valor_multiplicador.toList().size > 0){
                    cantidad_unidad_bruta = multiplicador_unidad * valor_multiplicador
                        .last()
                        .value
                        .replace(',', '.')
                        .filter { it.isDigit() || it.equals('.') }
                        .toFloat()
                }
            }
            if(nombre_unidad == null || cantidad_unidad_bruta == null){
                return null
            } else {
                println("La cantidad por unidad es: " + cantidad_unidad_bruta)
                return parseCantidadNetaCarrefour(nombre_unidad, cantidad_unidad_bruta)
            }
        }

        private fun parseCantidadNetaCarrefour(nombreUnidad: String, cantidad_bruta: Float): HashMap<String, Any>? {
            val result: HashMap<String, Any> = hashMapOf()
            when(nombreUnidad.lowercase()){
                "mg" -> {
                    result.put("tipounidad", TipoUnidad.GRAMOS)
                    result.put("peso", cantidad_bruta/1000)
                }
                "g" -> {
                    result.put("tipounidad", TipoUnidad.GRAMOS)
                    result.put("peso", cantidad_bruta)
                }
                "kg" -> {
                    result.put("tipounidad", TipoUnidad.GRAMOS)
                    result.put("peso", cantidad_bruta*1000)
                }
                "ml" -> {
                    result.put("tipounidad", TipoUnidad.ML)
                    result.put("peso", cantidad_bruta)
                }
                "cl" -> {
                    result.put("tipounidad", TipoUnidad.ML)
                    result.put("peso", cantidad_bruta*10)
                }
                "dl" -> {
                    result.put("tipounidad", TipoUnidad.ML)
                    result.put("peso", cantidad_bruta*100)
                }
                "l"-> {
                    result.put("tipounidad", TipoUnidad.ML)
                    result.put("peso", cantidad_bruta*1000)
                }
                "lt" -> {
                    result.put("tipounidad", TipoUnidad.ML)
                    result.put("peso", cantidad_bruta*1000)
                }
                else -> {
                    result.put("tipounidad", TipoUnidad.UNIDADES)
                    result.put("peso", cantidad_bruta)
                }
            }
            if(!result.isEmpty())
                return result
            else
                return null
        }
        private fun parseJsonPesoToProductoPesoDia(elemento: Productos.Producto): Map<String, Any>? {
            //De momento se puede utilizar el propio parser de Carrefour
            return parseJsonPesoToProductoPesoCarrefour(elemento)
        }

        private fun parseJsonPesoToProductoPesoMercadona(elemento: Productos.Producto):  Map<String, Any>? {
            //En mercadona no se debe de acudir al nombre, sino al precio_peso
            val precio_peso = elemento.precio_peso
            //Obtengo la primera aparición de un número seguido de todos los carácteres restantes.
            val pattern = Regex("[0-9]+[\\w\\s\\W]*")
            val match = pattern.find(precio_peso)
            if(match != null) {
                println("El pattern general encontrado es: " + match.value)
                return parseCantidadBrutaMercadona(match.value)
            }
            else
                return null
        }

        private fun parseCantidadBrutaMercadona(cantidad_texto: String): Map<String, Any>? {
            val cantidad_producto: HashMap<String, Any>?
            var tipo_unidad: String? = null
            var nombre_unidad: String? = null
            var multiplicador_unidad: Float = 1f
            var cantidad_unidad_bruta: Float? = null
            //Obtengo el tipo de unidad
            val pattern_tipoUnidad = Regex("boles|bol|quesitos|mallas|malla|minis|mini|saquitos|saquito|sacos|saco|porciones|porcion|porción|tabletas|tableta|barritas|barra|barras|frasquitos|frascos|frasco|piezas|pieza|tarrinas|tarrina|bandejas|bandeja|latas|lata|sticks|stick|botellin|botellines|botellas|botella|unidades|unidad|bote|botes|tarritos|tarros|tarro|sobre|sobres|envases|envase|paquete|paquetes|bolsitas|bolsas|bolsa|brick|bricks|briks|brik|mg|g|dg|cg|kg|ml|cl|dl|l|lt|ud", RegexOption.IGNORE_CASE)
            val secuencia_unidad = pattern_tipoUnidad.findAll(cantidad_texto)
            val pattern_cantidad = Regex("[0-9]+,?[0-9]*\\s*" + "(" + pattern_tipoUnidad + ")"  + "(\\s*(escurrido)?)?")
            var valor_multiplicador = pattern_cantidad.findAll(cantidad_texto)
            println("Las tipos unidades obtenidas" + " del nombre " + cantidad_texto +  "son: " )
            secuencia_unidad.toList().forEach { println("Sequencia: " + it.value) }
            println("Las cantidades obtenidas son: ")
            valor_multiplicador.toList().forEach { println("valor-Multiplicador: " + it.value)}
            //Debo evitar multiplicar valore scuando mercadona indica el peso bruto y el peso escurrido.
            if(
                valor_multiplicador.any{
                    it.value.contains("escurrido")
                }
            ){
                //Trato de encontrar que no ocurra aplicando a la primera aprición el patrón que no quiero.
                val pattern_escurrido = Regex("[0-9]+,?[0-9]*\\s*(mg|g|dg|cg|kg|ml|cl|dl|l|lt)")
                val result_escurrido = pattern_escurrido.find(valor_multiplicador.first().value)
                if(result_escurrido != null){
                    //Utilizo el no escurrido como peso final
                    valor_multiplicador = sequenceOf(valor_multiplicador.first())
                }
            }
            //En el caso de que se ahay encontrado al menos una ocurrencia se puede sacar las unidades.
            if(secuencia_unidad.toList().size > 0){
                nombre_unidad = secuencia_unidad.last().value
                //En el caso de que haya más de una ocurrencia, se obtiene el multiplicador de la primera.
                if(valor_multiplicador.toList().size > 1){
                    println("El valor del multiplicador es: " + valor_multiplicador.first().value)
                    multiplicador_unidad = valor_multiplicador
                        .first()
                        .value
                        .replace(',', '.')
                        .filter { it.isDigit() || it.equals('.') }
                        .toFloat()
                }
                //En el caso de que haya al menos una ocurrencia se obtiene la cantidad
                //de la multiplicaciń del multiplicador por la última ocurrencia.
                if(valor_multiplicador.toList().size > 0){
                    cantidad_unidad_bruta = multiplicador_unidad * valor_multiplicador
                        .last()
                        .value
                        .replace(',', '.')
                        .filter { it.isDigit() || it.equals('.') }
                        .toFloat()
                }
            }
            if(nombre_unidad == null || cantidad_unidad_bruta == null){
                return null
            } else {
                println("La cantidad por unidad es: " + cantidad_unidad_bruta)
                //A partir de aquí se aplica lo mismo de momento que en Carrefour
                return parseCantidadNetaCarrefour(nombre_unidad, cantidad_unidad_bruta)
            }
        }
    }
}