from ast import List, Str
from mimetypes import init
import time
from multiprocessing.connection import wait
import random
from carrefour.carrefour_scraping import Carrefour
import carrefour.constantes as conts
import os


class Main_Carrefour():

    def __init__(self,):
        global carrefour
        carrefour = Carrefour()
        global aux
        aux = 0   
        
    def execQuery(self, query) -> list: 
        with carrefour as bot:
            global aux
            bot.get_url_carrefour()
            if aux == 0:
                bot.esperar_cookies()
            bot.searchQuery(query)
            query_result= bot.getData(query = query)
            aux += 1
        return query_result
    
    def updateProductosCarrefour(self, outFile, prueba = -1) -> None:
        file = open("/home/david/AndroidStudioProjects/RecipeToShop2/Scraping/outputsCarrefour/" + outFile + ".txt", "w")
        file.write("{\n\"productos\":\n[\n")
        if prueba == -1:
            prueba = len(conts.ALIMENTOS)
        for alimento in conts.ALIMENTOS[0:prueba]:
            productos = self.execQuery(alimento)
            file.write("[\n")
            for producto in productos:
                file.write(producto)
                file.write(",\n")
            file.write("],\n")
        file.write("],\n}\n")        
        file.close()
        carrefour.close()
            
            
        
    

        