from ast import List, Str
from mimetypes import init
import time
from multiprocessing.connection import wait
import random
import carrefour.constantes as const 
from carrefour.carrefour_scraping import Carrefour

class main_Carrefour():
    
    def execQuery(self, query) -> list: 
        with Carrefour() as bot:
            bot.get_url_carrefour()
            bot.esperar_cookies()
            bot.searchQuery(query)
            query_result= bot.getData()
        return query_result
        
    

        