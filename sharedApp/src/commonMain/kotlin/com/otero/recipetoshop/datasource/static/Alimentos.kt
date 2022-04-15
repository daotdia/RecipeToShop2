package com.otero.recipetoshop.datasource.static

object Alimentos {
    fun alimentos(): List<String> {
        return alimentos.lines().map { it.lowercase() }
    }
    val alimentos =
        """Zumo con leche
Mostaza
Pan hamburguesa
Ketchup
Salsa cesar
Queso parmesano
Boquerón
Foie de pato
Hamburgesa caballo
Mermelada de melocotón
Fartons
Ajo
Muesli
Aguacate
Pan centeno
Palmeritas de hojaldre
Pimiento verde
Nata para cocinar
Yogur azucarado
Yogur natural
Yogur desnatado
Mousse de yogur
Yogur tipo griego
Yogur de soja
Petit-suisse
Yogur bebido 
Leche entera
Leche semidesnatada
Leche desnatada
Leche de oveja
Leche de soja
Leche de soja con chocolate
Leche de Almendras 
Leche sin lactosa entera
Leche con omega 3 desnatada
Leche condensada
Cuajada
Natilla de vainilla
Natilla de chocolate
Flan de huevo
Flan de vainilla
Crema catalana
Petit-suisse chocolate
Arroz con leche
Mousse de chocolate 
Batido de vainilla
Batido de fresa
Batido de chocolate
Queso fresco Burgos
Quesito
Queso en lonchas
Queso tierno
Queso semicurado
Queso curado
Queso crema
Queso crema light
Queso emmental
Queso mozzarela
Queso edam
Helado de nata 
Galleta digestiva
Galleta integral
Cereales 
Barrita de cereales
Pan blanco 
Pan rústico
Pan de molde
Pan Hotdog
Arroz
Harina de trigo
Fécula de maíz
Sémola de trigo seca
Sémola de arroz
Pasta
Cacao en polvo
Chocolate con leche
Chocolate blanco
Chocolate con arroz inflado
Crema de chocolate con avellanas
Kit-Kat
Bizcocho
Croissant
Ensaimada 
Bollo tipo donut
Magdalena 
Bollo relleno de chocolate
Rosquilletas
Bacalao
Lenguado
Lubina
Merluza
Rape
Pescadilla
Atún
Pez espada
Salmón
Trucha
Sardina
Surimi
Anchoa en lata aceite
Atún al natural escurrido
Atún en aceite escurrido
Sardina en aceite escurrid
Calamar
Mejillón en escabeche
Sepia
Chuleta de cerdo
Lomo de cerdo
Solomillo de cerdo
Costilla de cerdo
Codillo de cerdo
Costilla ternera
Entrecot de ternera
Paletilla ternera
Solomillo ternera
Hígado de ternera
Chuleta cordero
Hígado cordero
Pollo sin piel
Muslo de pollo
Pechuga de pollo
Higado de pollo
Conejo
Carne picada
Longaniza
Longaniza de magro
Longaniza de pavo
Chorizo rojo
Hamburguesa de ternera
Frankfurt
Morcilla
Jamón Serrano
Jamón Cocido
Pechuga de pavo
Chorizo en lonchas
Fuet
Lomo embuchado
Sobrasada
Mortadela
Solomillo de buey
Albóndigas
Salami
Huevo mediano
Huevo frito
Yema huevo
Clara de huevo
Tortilla de patata
Tortilla a la francesa
Guisantes
Alubia blanca cocida
Alubia blanca cruda
Lenteja cruda
Lenteja cocida
Garbanzo cocido
Patata 
Patata hervida
Patatas fritas
Almendra 
Cacahuete
Nueces
Pistachos
Pipas
Avellanas
Quicos
Kiwi
Mandarina
Manzana
Naranja
Pera, sin piel
Piña
Plátano
Melocotón
Albaricoque
Caqui
Cereza
Fresón
Nectarina
Sandia
Uva verde
Granada
Acelga
Apio
Col de bruselas
Col repollo
Endibia
Escarola
Espárrago blanco en conserva
Espárrago verde
Espinacas
Lechuga
Maíz dulce
Pepino
Tomate 
Chirivía
Champiñón
Nabo
Rábano
Zanahoria
Cebolla 
Puerro
Alcachofa
Berenjena 
Calabacín 
Calabaza 
Judía verde cruda
Judía verde hervida
Pimiento rojo
Aceituna sin hueso
Aceite de oliva
Aceite de girasol 
Aceite de maíz 
Tomate triturado
Tomate frito
Salsa boloñesa
Salsa barbacoa
Salsa carbonara
Nata montada
Mantequilla
Miel
Mayonesa
Paté de campaña
Azúcar
Azucar moreno
Caldo de Pollo
Tortelini de carne
Tortelini 4 quesos
Canelones de carne
Lasaña Boloñesa
Paella
Arroz Negro
Pizza
Croqueta de Pollo
Croqueta de Bacalao
Croqueta de Jamón 
San Jacobo
Nuggets de pollo
Empanadilla de atún
Empanadilla de carne
Masa de hojaldre
Masa de pizza
Churros
Ensaladilla rusa
Muslito de mar
Pannini de jamón y queso
Varitas de merluza
Aritos de chocolate 
Zumo de manzana
Zumo de piña
Zumo de naranja
Zumo de manzana
Zumo de melocotón
Galletita salada
Gusanitos de maíz
Helado tipo almendrado
Rosquilletas con pipas
Bebida isotónica
Papas
Huevo de chocolate
Chocolate a la taza
Gominolas
Tortitas de maíz
Membrillo
Horchata
Cereales en polvo
Leche infantil
Potito
Leche infantil junior
Leche infantil 2 años
Leche infantil 3 años
Bacón ahumado
Café con leche
Batido lácteo
Callos de ternera
Cerdo lomo
Queso Tetilla
Queso Castellano
Queso de Arzúa
Queso Ibores
Queso Mahón
Queso Zamorano
Queso torta del casar
Queso San Simón
Queso majorero
Queso Raclette
Tomate entero enlatado
Tomate triturado
Boniato
Berro
Achicoria
Anguila
Apio
Arenque
Atún
Lata Atún
Bacaladilla
Bacalao
Aceite de algodón
Aceite de cacahuete
Aceite de coco
Aceite de germen de trigo
Aceite de nuez
Aceite de palma
Aceite de sésamo
Aceite de colza
Aceite de hígado de bacalao
Mantequilla salada
Huevo de codorniz
Huevo de gallina
Huevo de pavo
Barra chocolate
Barrita cereales maíz y trigo
Barrita cereales trigo y chocolate
Mermelada de albaricoque
Mermelada de ciruela
Mermelada de grosella roja
Mermelada de mora
Mermelada de naranja
Chocolate con leche y almendras
Chocolate con leche y arroz
Chocolate con nueces de macadamia
Chocolate negro almendra
Chocolate negro
Crema inglesa
Turrón
Fructosa
Crema pastelera
Alioli
Zumo de uva y melocotón
Caldo vegetal
Chile rojo
Chile verde
Bacalao
Bacalao salado
Bígaro
Bogavante
Bonito del norte
Bonito enlatado en aceite
Breca
Caballa
Caballa enlatada en aceite
Cabracho o rascacio
Calamar
Cangrejo de río
Cangrejo en conserva
Carpa
Caviar
Centollo
Dorada
Gamba congelada
Gamba cruda
Jurel
Langostino
Lenguado
Lija
Lucio
Mejillón en conserva al natural
Mejillón hervido
Merluza congelada
Mero
Mújol
Ostra
Palometa
Perca
Percebe 
Pescadilla
Pez espada
Platija
Rape
Sardina enlatada en aceite
Sardina enlatada en escabeche
Sargo
Tenca
Trucha
Vieira
Vieja
Volador
Soja germinada
Cebollino 
Col rizada
Salsa vinagreta
Salsa siciliana
Salsa napolitana
Salsa carbonara
Coliflor
Espinaca
Salsa boloñesa
Salsa bechamel
Salsa barbacoa
Salsa al roquefort
Sal de mar
Pimienta negra
Pimienta blanca
Pimentón en polvo
Patatas chips
Palomitas de maíz
Menta fresca
Mayonesa 
Galletas saladas
Flan de huevo
Crema de vainilla y mousse de chocolate
Judía verde
Maíz
Nabo
Níscalo
Palmito
Puré de patata y queso
Puré de patata con leche
Puré de patata con verduras
Tapioca
Almidón de arroz
Trufa
Almidón de trigo
Almendra cruda
Almendra frita 
Alubia blanca
Alubia negra
Crema de coco
Crema de chocolate
Crema de chocolate y nata
Arroz integral
Coco desecado
Coco fresco
Leche de coco
Leche en polvo semidesnatada
Leche merengada
Frambuesa cruda
Leche desnatada 
Leche semidesnatada 
Mousse de queso fresco con frutas
Mousse de queso fresco
Mousse de yogur con frutas
Mousse de yogur natural
Anacardo
Petit suïsse
Cacahuete frito 
Petit suisse fresa
Petit suisse líquido
Queso Camembert 20-30% MG/ES
Queso oveja y cabra
Queso oveja, vaca y cabra
Queso oveja y vaca
Queso fresco cabra
Castaña
Limón zumo
Guayaba
Queso fundido
Queso Gouda
Queso Manchego
Macedonia de frutas
Queso Munster
Queso para untar con finas hierbas
Melocotón
Yogur griego
Yogur líquido
Yogur líquido desnatado
Yogur líquido con cereales
Yogur, desnatado, aromatizado, sabor s/e
Yogur, desnatado, con cereales
Níspero,
Cerdo solomillo
Ciervo
Conejo
Jamón cocido
Corazón de cordero
Hígado, de cerdo
Paté de pimienta
Pato
Corazón de pollo
Chufa
Corazón de cerdo
Corazón de vaca/buey
Cordero pierna
Galllina
Pichón
Oca
Crema de cacahuete
Pollo entero
Vaca/buey solomillo
Salchicha viena
Lengua de buey
Ternera solomillo
Lengua de ternera
Liebre entera
Salchicha fresca 
Ternera
Pavo pechuga
Ternera lomo
Pollo muslo
Pollo pechuga
Vaca/buey
Grosella
Leche de cabra
Lima
Leche de oveja
Litchi
Queso Cheddar
Membrillo
Petit Suisse
Papaya
Avena
Queso Gruyer
Almidón de maíz
Pera enlatada en almíbar
Cebada
Agua con gas
Uva negra
Café en grano o molido
Garbanzo
Cerdo solomillo
Anís seco
Café en polvo soluble
Néctar de ciruela
Ternera solomillo
Ternera costilla
Cereales desayuno  maíz y miel
Cereales desayuno  maíz y trigo
Cereales desayuno trigo azucarado
Cereales desayuno  trigo y arroz
Cereales desayuno  trigo y frutas
Cereales desayuno trigo y miel
Cereales desayuno  trigo, avena, maiz y miel
Cereales desayuno  trigo, avena, maíz, miel y nueces
Cereales desayuno maíz azucarado
Croissant de chocolate
Donut
Galleta  "digestiva"
Sesos de ternera
Galleta barquillo
Guisante
Galleta cubierta de chocolate
Galletas de mantequilla
Hígado vaca/buey
Harina de avena
Harina de cebada
Masa de hojaldre
Pavo muslo
Néctar de mango
Napolitana
Riñón de cordero
Pan blanco barra
Lenteja
Pan blanco baguette
Pan de avena
Zumo de piña y uva
Riñón de ternera
Pan de cebada
Pan de maíz
Pasta con huevo
Pasta vegetales
Pasta integral
Sesos de cerdo
Pasta integral
Pasta rellena
Pastel de manzana
Zumo de pomelo
Sesos, de cordero
Zumo de mango
Pudin de pasas
Quinoa
Sémola de trigo
Sobao
Soja harina 
Trigo
Zumo de lima
Zumo de grosella roja
Zumo de grosella negra
Vodka
Tequila
Té
Vermut
Ron
Refresco naranja
Refresco limón
Néctar de pomelo
Néctar de piña
Néctar de naranja
Néctar de melocotón
Néctar de maracuyá
Néctar de albaricoque
Coñac
Cerveza oscura
Soda
Germen de trigo
Harina de centeno
Harina de trigo integral
Maíz en mazorca
Pasta
Arroz hinchado
Tofu
Soja
Haba
Soja fresca
Harina de soja
Lenteja en conserva
Sésamo semilla
Yogur enriquecido natural
Yogur enriquecido con frutas
Yogur bebido natural azucarado
Yogur desnatado con cereza y frambuesa
Yogur, desnatado, con frutas del bosque
Yogur, desnatado, con frutas
Yogur, desnatado, con frutas tropicales
Yogur, desnatado, con manzana
Yogur, enriquecido, natural
Yogur, desnatado, con melocotón y maracuyá
Yogur, enriquecido, natural, azucarado
Yogur, desnatado, con piña y pomelo
Yogur, enriquecido, sabor, s/e
Yogur, líquido, desnatado, natural
Crema de almendras
Yogur, desnatado, sabor vainilla
Yogur, entero, con fresas
Yogur líquido, con frutas s/e
Fuet
Calamar conserva
Guayaba 
Piña
Kefir
Ciruela
Pescadilla
Guisante congelado
Alcachofa
Brécol
Col lombarda
Espárrago
Col de Bruselas
Perejil
Calabaza
Puré de patata
Grosella negra
Mango
Centeno
Cereales desayuno maíz, trigo y avena
Zumo bifrutas fresa y plátano
Zumo bifrutas Ibiza
Zumo bifrutas Tropical
Zumo Yosport
Leche sin lactosa semidesnatada
Leche sin lactosa desnatada"""
}