
import carrefour.main_carrefour as carrefourScrap
import mercadona.main_mercadona as mercadonaScrap
import dia.main_dia as diaScrap

#carrefour = carrefourScrap.Main_Carrefour()
#carrefour.updateProductosCarrefour(outFile = "Prueba1")

#mercadona = mercadonaScrap.Main_Mercadona()
#mercadona.updateProductosMercadona(outFile= "Prueba3")

dia = diaScrap.Main_Dia()
dia.updateProductosMercadona(outFile= "Prueba1")