import os
from shlex import join
import time
import random
from numpy import prod
import selenium
import pandas as pd
from typing import List
from alimento import Alimento
from selenium.webdriver.remote import webelement
from selenium.webdriver.common.proxy import Proxy, ProxyType
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support import expected_conditions as ec
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
import dia.constantes as diaConsts
import carrefour.constantes as consts
from selenium import webdriver
from selenium.common.exceptions import StaleElementReferenceException, NoSuchElementException, TimeoutException
from http_request_randomizer.requests.proxy.requestProxy import RequestProxy
from selenium.webdriver.common.keys import Keys
from Util.Tools import checkContent as check
import unidecode

class Dia(webdriver.Firefox):
    def __init__(self, driver_path='/home/david/AndroidStudioProjects/RecipeToShop2/Scraping', teardown=False):
        self.driver_path=driver_path
        self.teardonw=teardown
        os.environ['PATH']+=os.pathsep+self.driver_path
        
        super(Dia, self).__init__()
        
        #Utilizo proxys dinámicos.
        #req_proxy = RequestProxy() 
        #proxies = req_proxy.get_proxy_list() 
        #PROXY = proxies[0].get_address()
        self.randomize_proxy()
        
        self.maximize_window()
        self.implicitly_wait(5)

    def randomize_proxy(self):
        PROXY = random.choice(consts.PROXIES)
        prox = Proxy()
        prox.proxy_type = ProxyType.MANUAL
        prox.autodetect = False
        capabilities = self.capabilities
        prox.http_proxy = PROXY
        prox.ssl_proxy = PROXY
        prox.add_to_capabilities(capabilities)
        
    def __exit__(self, exce_type, exc_val, exc_tb):
        if self.teardonw:
            self.quit()

    def get_url_dia(self):
        self.get(diaConsts.BASE_URL)
        
    def esperar_cookies(self):
        #Espero a que se cargue la barra de búsqueda de Carrefour.
        cockies = WebDriverWait(self,10).until(
            ec.presence_of_element_located((By.XPATH, '//button[@id="onetrust-accept-btn-handler"]'))
        )
        cockies.click()
        time.sleep(1)
        
    def searchQuery(self, query):        
        #Obtengo el campo de búsqueda de Mercadona tras esperar 5 segundos y añado la query.
        WebDriverWait(self, 5).until(
            ec.visibility_of_all_elements_located(
                (By.XPATH, '//input[@class="text ui-autocomplete-input"]')
                )
            )
        input_query = self.find_element(By.XPATH,'//input[@class="text ui-autocomplete-input"]')
        input_query.send_keys(query)
        bottom_query = self.find_element(By.XPATH,'//input[@class="button desktop-search"]')
        bottom_query.click()
    
    def getData(self, query) -> list:
        incompleto = False
        data: List[Alimento] = []
        result = []

        #Espero 4 segundos.
        time.sleep(4)
        
        #Obtengo todas las células de alimentos de la tabla de búsqueda
        productos = self.find_elements_by_class_name("productMainLink")
        print("El número de productos encontrados es: " + str(len(productos)))
        for index,producto in enumerate(productos):
            try:
                if index > 10:
                    break
                nombre: WebElement = producto.find_element_by_class_name("details")#Hay que buscar el texto
                #print(nombre.text)
                imagen: WebElement = producto.find_element_by_tag_name('img')#Hay que obtener el atributo src
                #print("La imagen es: " + imagen.get_attribute("src"))
                precio_peso: WebElement = producto.find_element_by_class_name("pricePerKilogram")#Hay que buscar el texto
                #print("El peso es: " + ' '.join([peso.text for peso in precio_peso]))
                precio: WebElement = producto.find_element_by_class_name("price")#Hay que buscar el texto
                #print("El precio es: " + precio.text)
                #El buscar la oferta demora mucho tiempo.
                #oferta: WebElement = articulo.find_element_by_class_name("ebx-result__banner")#Hay que buscar texto
                oferta = None
                
                if not incompleto:
                    alimento = Alimento.build_alimento(
                        query = query,
                        nombre= unidecode.unidecode(nombre.text),
                        imagen_src = imagen.get_attribute("src"),
                        precio = precio.text,
                        precio_peso = precio_peso.text,
                        oferta = oferta.text if oferta is not None else '',
                        supermercado = "Dia"
                    )
                    data.append(alimento)
            
            except StaleElementReferenceException:
                incompleto = True
                print('No encuentra un elemento porque ha desaparecido antes de tiempo')
            except NoSuchElementException:
                incompleto = True
                print('No encuentra un elemento por alguna razón')
            except TimeoutException:
                incompleto = True
                print('No encuentra un elemento porque se ha esperado demasiado')
        
        for alimento in data:
            if (check(query = query, nombre = alimento.nombre)):
                result.append(alimento.alimento_JSON())
        return result
        
    
        
        
        
    
    