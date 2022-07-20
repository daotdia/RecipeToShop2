from ast import List, Str
from mimetypes import init
import time
from multiprocessing.connection import wait
import random
from dia.dia_scraping import Dia
import carrefour.constantes as conts
import os

class Main_Dia():
    def __init__(self,):
        global dia
        dia = Dia()
        global aux
        aux = 0   
        
    def execQuery(self, query) -> list: 
        with dia as bot:
            global aux
            if aux == 0:
                bot.get_url_dia()
                bot.esperar_cookies()
            bot.searchQuery(query = query)
            query_result= bot.getData(query = query)
            aux += 1
        return query_result
    
    def updateProductosMercadona(self, outFile, prueba = -1) -> None:
        file = open("/home/david/AndroidStudioProjects/RecipeToShop2/Scraping/outputsDia/" + outFile + ".txt", "w")
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
        dia.close()
            
            