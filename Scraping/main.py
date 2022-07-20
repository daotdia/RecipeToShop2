
import carrefour.main_carrefour as carrefourScrap
import mercadona.main_mercadona as mercadonaScrap
import dia.main_dia as diaScrap

#carrefour = carrefourScrap.Main_Carrefour()
#mercadona = mercadonaScrap.Main_Mercadona()
dia = diaScrap.Main_Dia()

#carrefour.updateProductosCarrefour(outFile = "Prueba1", prueba = 5)
#mercadona.updateProductosMercadona(outFile= "Prueba3", prueba = 5)
dia.updateProductosMercadona(outFile= "Prueba1", prueba = 5)