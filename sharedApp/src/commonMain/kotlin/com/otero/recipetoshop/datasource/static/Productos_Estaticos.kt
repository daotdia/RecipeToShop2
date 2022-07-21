package com.otero.recipetoshop.datasource.static

import kotlinx.serialization.json.*

object Productos_Carrefour {
    val productos: String = """
    {
       "productos":[
          [
             {
                "query":"Zumo con leche",
                "nombre":"Zumo de frutas y leche Carrefour sabor mediterraneo pack de 6 briks de 20 cl.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/902206_00_1.jpg",
                "precio_peso":"0,83 \u20ac/l",
                "precio":"0,99 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Zumo con leche",
                "nombre":"Zumo de fruta y leche Carrefour Tropical pack de 6 briks de 20 cl.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/902174_00_1.jpg",
                "precio_peso":"0,88 \u20ac/l",
                "precio":"1,05 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Zumo con leche",
                "nombre":"Zumo de frutas y leche Carrefour sabor tropical pack de 3 briks de 33 cl.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/902172_00_1.jpg",
                "precio_peso":"0,96 \u20ac/l",
                "precio":"0,95 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Zumo con leche",
                "nombre":"Zumo de fruta y leche Carrefour Mediterraneo pack de 3 briks de 33 cl.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/902190_00_1.jpg",
                "precio_peso":"1,03 \u20ac/l",
                "precio":"1,02 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Zumo con leche",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/902169_00_1.jpg",
                "precio_peso":"",
                "precio":"0,92 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Zumo con leche",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/902171_00_1.jpg",
                "precio_peso":"",
                "precio":"0,92 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             }
          ],
          [
             {
                "query":"Mostaza",
                "nombre":"Mostaza de Dijon Maille 215 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/002263_00_1.jpg",
                "precio_peso":"14,19 \u20ac/kg",
                "precio":"3,05 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Mostaza",
                "nombre":"Mostaza Orlando sin gluten envase 290 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/002259_00_1.jpg",
                "precio_peso":"8 \u20ac/kg",
                "precio":"0,99 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Mostaza",
                "nombre":"Mostaza original Prima sin gluten envase 330 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/711556_00_1.jpg",
                "precio_peso":"5,42 \u20ac/kg",
                "precio":"1,79 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Mostaza",
                "nombre":"Mostaza Heinz envase 220 ml.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/890418_00_1.jpg",
                "precio_peso":"7,63 \u20ac/l",
                "precio":"1,83 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Mostaza",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/002264_00_1.jpg",
                "precio_peso":"",
                "precio":"",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Mostaza",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/482906_00_1.jpg",
                "precio_peso":"",
                "precio":"",
                "oferta":"",
                "supermercado":"Carrefour"
             }
          ],
          [
             {
                "query":"Pan hamburguesa",
                "nombre":"Pan de hamburguesa maxi Carrefour 4 ud.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/447078_00_1.jpg",
                "precio_peso":"3,17 \u20ac/kg",
                "precio":"0,95 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Pan hamburguesa",
                "nombre":"Pan de hamburguesa rustica XXL 4 ud",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/067400_00_1.jpg",
                "precio_peso":"4,47 \u20ac/kg",
                "precio":"1,79 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Pan hamburguesa",
                "nombre":"Pan de Hamburguesa Carrefour 4 ud 300 g",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/289641_00_1.jpg",
                "precio_peso":"5,97 \u20ac/kg",
                "precio":"1,79 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Pan hamburguesa",
                "nombre":"Pan de hamburguesa Juanito Baker 150 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/024197_00_1.jpg",
                "precio_peso":"11,80 \u20ac/kg",
                "precio":"1,77 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Pan hamburguesa",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/024178_00_1.jpg",
                "precio_peso":"11,80 \u20ac/kg",
                "precio":"1,77 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Pan hamburguesa",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/025658_00_1.jpg",
                "precio_peso":"11,80 \u20ac/kg",
                "precio":"1,77 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             }
          ],
          [
             {
                "query":"Ketchup",
                "nombre":"Ketchup Carrefour envase 560 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/438870_00_1.jpg",
                "precio_peso":"1,61 \u20ac/kg",
                "precio":"0,90 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Ketchup",
                "nombre":"Ketchup Heinz envase 570 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/507178_00_1.jpg",
                "precio_peso":"5,53 \u20ac/kg",
                "precio":"3,15 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Ketchup",
                "nombre":"Ketchup Heinz envase 700 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/680150_00_1.jpg",
                "precio_peso":"4,61 \u20ac/kg",
                "precio":"3,23 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Ketchup",
                "nombre":"Ketchup Orlando Barrilito 300 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/002212_00_1.jpg",
                "precio_peso":"3,30 \u20ac/kg",
                "precio":"0,99 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Ketchup",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/833020_00_1.jpg",
                "precio_peso":"",
                "precio":"1,50 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Ketchup",
                "nombre":"",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/438868_00_1.jpg",
                "precio_peso":"",
                "precio":"0,99 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             }
          ],
          [
             {
                "query":"Salsa cesar",
                "nombre":"Salsa cesar Carrefour envase 300 g.",
                "imagen_src":"https://static.carrefour.es/hd_350x_/img_pim_food/247396_00_1.jpg",
                "precio_peso":"4,03 \u20ac/kg",
                "precio":"1,21 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             },
             {
                "query":"Salsa cesar",
                "nombre":"Mini Filetes Cesar En Salsa - 4 Variedades - Para Perros Adultos - 4 X 150 G (x6)",
                "imagen_src":"https://static.carrefour.es/hd_350x_/imagenes/products/30658/90135/086/3065890135086/imagenGrande1.jpg",
                "precio_peso":"",
                "precio":"77,71 \u20ac",
                "oferta":"",
                "supermercado":"Carrefour"
             }
          ]
       ]
    }""".trimIndent()

    val json: JsonElement = Json.parseToJsonElement(productos)
}

object productos_Mercadona{
    val productos: String = """
      {
   "productos":[
      [
         
      ],
      [
         {
            "query":"Mostaza",
            "nombre":"Mostaza Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/922d4b9b2988edbca3995c5aac462dbc.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Bote 330 g",
            "precio":"0,68 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Mostaza",
            "nombre":"Mostaza de Dijon Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/4334494341409213b190f94f81e8baa5.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Tarro 200 g",
            "precio":"0,68 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Mostaza",
            "nombre":"Mostaza a la antigua Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/5f0836b8ae9dde88e3b78a16b6208370.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Tarro 200 g",
            "precio":"0,68 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Mostaza",
            "nombre":"Salsa miel y mostaza Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/33c508f6f4f26592f8d1de064152418e.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Bote 300 ml",
            "precio":"0,68 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         }
      ],
      [
         {
            "query":"Pan hamburguesa",
            "nombre":"Pan de hamburguesa Hacendado sin sesamo",
            "imagen_src":"https://prod-mercadona.imgix.net/images/17d0193cde755d314bafdf251ed3a05a.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Paquete 4 ud. (220 g)",
            "precio":"0,95 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Pan hamburguesa",
            "nombre":"Pan de hamburguesa Brioche Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/94dd76a734b1016bc1f2668b1d7592ba.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Paquete 2 ud. (110 g)",
            "precio":"0,95 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Pan hamburguesa",
            "nombre":"Pan de hamburguesa 100% integral Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/e30d2418fe02f24d55507ee44fc8ee78.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Paquete 4 ud. (330 g)",
            "precio":"0,95 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Pan hamburguesa",
            "nombre":"Pan de hamburguesa sin gluten Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/96c309d8528baaeaddb59173e0ff8df3.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Paquete 2 ud. (160 g)",
            "precio":"0,95 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         }
      ],
      [
         {
            "query":"Ketchup",
            "nombre":"Ketchup Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/560d3c1a007e5ab9c97cc0bb2b23b206.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Bote 600 g",
            "precio":"0,90 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Ketchup",
            "nombre":"Ketchup Heinz",
            "imagen_src":"https://prod-mercadona.imgix.net/images/92db1df1b680751776c32ac8d08aae43.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Bote 570 g",
            "precio":"0,90 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Ketchup",
            "nombre":"Ketchup light Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/dd097a5fd8cc73aea8c786688c3b0b4e.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Bote 600 g",
            "precio":"0,90 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Ketchup",
            "nombre":"Ketchup en sobres individuales Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/3fc6fdc9a1d193f609ab69e84f56c7cd.jpg?fit=crop&h=300&w=300",
            "precio_peso":"12 sobres x 20 g",
            "precio":"0,90 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         },
         {
            "query":"Ketchup",
            "nombre":"Varitas sabor ketchup Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/9c5badb5fc0b856191a9a75d06e79a84.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Paquete 75 g",
            "precio":"0,90 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         }
      ],
      [
         {
            "query":"Salsa cesar",
            "nombre":"Salsa cesar Hacendado",
            "imagen_src":"https://prod-mercadona.imgix.net/images/56d2fc7b9308f806f2d7ed2b010cbe44.jpg?fit=crop&h=300&w=300",
            "precio_peso":"Bote 310 ml",
            "precio":"1,25 \u20ac",
            "oferta":"",
            "supermercado":"Mercadona"
         }
      ]
   ]
}""".trimIndent()

    val json = Json.parseToJsonElement(productos)

}

object productos_Dia{
    val productos: String = """
        {
           "productos":[
              [
                 
              ],
              [
                 {
                    "query":"Mostaza",
                    "nombre":"MAILLE mostaza frasco 210 gr",
                    "imagen_src":"https://s0.dia.es/medias/h86/h9d/11147528241182.jpg",
                    "precio_peso":"14,24 \u20ac/Kg.",
                    "precio":"2,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"PRIMA mostaza americana bote 300 gr",
                    "imagen_src":"https://s3.dia.es/medias/hde/h4b/11147542659102.jpg",
                    "precio_peso":"4,82 \u20ac/Kg.",
                    "precio":"1,59 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"HEINZ mostaza suave bote 220 ml",
                    "imagen_src":"https://s1.dia.es/medias/h61/he6/11131378827294.jpg",
                    "precio_peso":"9,05 \u20ac/l.",
                    "precio":"1,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"MAILLE mostaza dijon original tarro 215 gr",
                    "imagen_src":"https://s1.dia.es/medias/productimages/h52/h8a/11146324869150.jpg",
                    "precio_peso":"13,91 \u20ac/Kg.",
                    "precio":"2,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"PRIMA salsa mostaza cero bote 325 gr",
                    "imagen_src":"https://s1.dia.es/medias/productimages/h80/h79/11147654332446.jpg",
                    "precio_peso":"6,12 \u20ac/Kg.",
                    "precio":"1,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"COOSUR mostaza al estilo americano antigoteo bote 300 ml",
                    "imagen_src":"https://s2.dia.es/medias/h90/h8f/9560187240478.jpg",
                    "precio_peso":"3,83 \u20ac/l.",
                    "precio":"1,15 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"FRIT RAVICH cocktail con miel y mostaza bolsa 360 gr",
                    "imagen_src":"https://s2.dia.es/medias/productimages/h03/h0e/11028458405918.jpg",
                    "precio_peso":"8,03 \u20ac/Kg.",
                    "precio":"2,89 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Mostaza",
                    "nombre":"DIA SALSEO salsa cualitativa de eneldo y mostaza frasco 200 gr",
                    "imagen_src":"https://www.dia.es/compra-online/_ui/desktop/theme-dia/images/dia/svg/image-not-available.svg",
                    "precio_peso":"9,38 \u20ac/Kg.",
                    "precio":"1,97 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 }
              ],
              [
                 {
                    "query":"Pan hamburguesa",
                    "nombre":"BIMBO pan de hamburguesas 4 uds bolsa 300 gr",
                    "imagen_src":"https://s1.dia.es/medias/productimages/hd0/hec/11137039826974.jpg",
                    "precio_peso":"8,30 \u20ac/Kg.",
                    "precio":"2,49 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Pan hamburguesa",
                    "nombre":"EL MOLINO DE DIA pan de hamburguesas 4 unidades bolsa 220 gr",
                    "imagen_src":"https://s0.dia.es/medias/h16/h76/10567166492702.jpg",
                    "precio_peso":"4,55 \u20ac/Kg.",
                    "precio":"1,00 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Pan hamburguesa",
                    "nombre":"EL MOLINO DE DIA pan de hamburguesa maxi 4 unidades bolsa 300 gr",
                    "imagen_src":"https://s0.dia.es/medias/h43/h34/10567167475742.jpg",
                    "precio_peso":"3,30 \u20ac/Kg.",
                    "precio":"0,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 }
              ],
              [
                 {
                    "query":"Ketchup",
                    "nombre":"DIA ketchup bote 560 gr",
                    "imagen_src":"https://s1.dia.es/medias/h30/h4e/9667530358814.jpg",
                    "precio_peso":"1,59 \u20ac/Kg.",
                    "precio":"0,89 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"TOSFRIT apetinas ketchup bolsa 90GRS",
                    "imagen_src":"https://s3.dia.es/medias/productimages/hc2/h5f/10682713866270.jpg",
                    "precio_peso":"13,00 \u20ac/Kg.",
                    "precio":"1,17 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"HELLMANN'S ketchup bote 430 ml",
                    "imagen_src":"https://s2.dia.es/medias/productimages/h00/h24/10948352573470.jpg",
                    "precio_peso":"3,60 \u20ac/Kg.",
                    "precio":"1,75 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"DIA ketchup bote 340 gr",
                    "imagen_src":"https://s1.dia.es/medias/h1c/h2a/9592242962462.jpg",
                    "precio_peso":"2,32 \u20ac/Kg.",
                    "precio":"0,79 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"ORLANDO ketchup bote 300 gr",
                    "imagen_src":"https://s1.dia.es/medias/productimages/h04/h0a/11105553645598.jpg",
                    "precio_peso":"3,30 \u20ac/Kg.",
                    "precio":"0,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"DIA ketchup light bote 540 gr",
                    "imagen_src":"https://s3.dia.es/medias/he1/h4b/9667527737374.jpg",
                    "precio_peso":"1,94 \u20ac/Kg.",
                    "precio":"1,05 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"PRIMA ketchup clasico bote 600 gr",
                    "imagen_src":"https://s3.dia.es/medias/hb9/hf1/11147542986782.jpg",
                    "precio_peso":"3,98 \u20ac/Kg.",
                    "precio":"2,39 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"HEINZ ketchup light botella 550 gr",
                    "imagen_src":"https://s0.dia.es/medias/h75/h29/11131388330014.jpg",
                    "precio_peso":"6,53 \u20ac/Kg.",
                    "precio":"3,59 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"PRIMA ketchup cero bote 570 gr",
                    "imagen_src":"https://s0.dia.es/medias/productimages/h67/h77/11147652366366.jpg",
                    "precio_peso":"4,54 \u20ac/Kg.",
                    "precio":"2,59 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 },
                 {
                    "query":"Ketchup",
                    "nombre":"HEINZ Ketchup top down bote 400 gr",
                    "imagen_src":"https://s0.dia.es/medias/productimages/haa/hd8/11131561148446.jpg",
                    "precio_peso":"6,50 \u20ac/Kg.",
                    "precio":"2,99 \u20ac",
                    "oferta":"",
                    "supermercado":"Dia"
                 }
              ],
              [
                 
              ]
           ]
        }
    """.trimIndent()

    val json = Json.parseToJsonElement(productos)
}