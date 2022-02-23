import os
import time
import random
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
import carrefour.constantes as conts
from selenium import webdriver
from selenium.common.exceptions import StaleElementReferenceException, NoSuchElementException, TimeoutException
from http_request_randomizer.requests.proxy.requestProxy import RequestProxy
from selenium.webdriver.common.keys import Keys
#from carrefour.format_output import ParseOutput

class Carrefour(webdriver.Firefox):
    def __init__(self, driver_path='/home/david/AndroidStudioProjects/RecipeToShop2/Scraping', teardown=False):
        self.driver_path=driver_path
        self.teardonw=teardown
        os.environ['PATH']+=os.pathsep+self.driver_path
        
        super(Carrefour, self).__init__()
        
        #Utilizo proxys dinámicos.
        #req_proxy = RequestProxy() 
        #proxies = req_proxy.get_proxy_list() 
        #PROXY = proxies[0].get_address()
        self.randomize_proxy()
        
        self.maximize_window()
        self.implicitly_wait(5)

    def randomize_proxy(self):
        PROXY = random.choice(conts.PROXIES)
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

    def get_url_carrefour(self):
        self.get(conts.BASE_URL)
        
    def esperar_cookies(self):
        #Espero a que se cargue la barra de búsqueda de Carrefour.
        cockies = WebDriverWait(self,10).until(
            ec.presence_of_element_located((By.XPATH, '//button[@id="onetrust-accept-btn-handler"]'))
        )
        cockies.click()
        time.sleep(1)
        
    def searchQuery(self, query):
        #Primero randomizo el proxy.
        self.randomize_proxy()
        input_query = self.find_element(By.XPATH,'//input[@title="Buscar en Carrefour"]')
        input_query.click()
        input_query = WebDriverWait(self,10).until(
            ec.presence_of_element_located((By.XPATH,'//input[@aria-label="Caja de búsqueda"]'))
        )
        #Envío la query a la barra de búsqueda.
        input_query.send_keys(query)
        #Obtengo el símbolo de búsqueda para hacerle click.
        bottom_search = WebDriverWait(self,10).until(
            ec.presence_of_element_located((By.XPATH,'//button[@class="ebx-event-button ebx-search-button ebx-search-box__search-button"]'))
        )
        bottom_search.click()
    
    def getData(self) -> list:
        data: List[Alimento] = []
        result= []
        incompleto = False
        try:
            #Obtengo la seccion de artículos
            WebDriverWait(self,10).until(
                ec.presence_of_element_located((By.XPATH, '//section[@name="ebx-grid-item--animation"]'))
            )
            #Hago que la lista se cargue bien y pueda hacer scroll en la misma.
            #element = self.find_elements(By.XPATH, '//main[@class="ebx-empathy-x__main ebx-scroll"]')
            #self.find_element_by_css_selector("body").send_keys(Keys.END)
        
            articulos = self.find_elements_by_class_name("ebx-result__wrapper")
            for index,articulo in enumerate(articulos):
                if index > 5:
                    break
                nombre: WebElement = articulo.find_element_by_tag_name('h1')#Hay que buscar el texto
                imagen: WebElement = articulo.find_element_by_tag_name('img')#Hay que obtener el atributo src
                precio: WebElement = articulo.find_element_by_class_name('ebx-result-price__value')#Hay que buscar el texto
                precio_peso: WebElement = articulo.find_element_by_class_name("ebx-result__quantity")#Hay que buscar el texto
                #El buscar la oferta demora mucho tiempo.
                #oferta: WebElement = articulo.find_element_by_class_name("ebx-result__banner")#Hay que buscar texto
                oferta = None
        except StaleElementReferenceException:
            incompleto = True
            print('No encuentra un elemento')
        except NoSuchElementException:
            incompleto = True
            print('No encuentra un elemento')
        except TimeoutException:
            incompleto = True
            print('No encuentra un elemento')
        if not incompleto:
            alimento = Alimento.build_alimento(
                nombre= nombre.text,
                imagen_src = imagen.get_attribute("src"),
                precio = precio.text,
                precio_peso = precio_peso.text,
                oferta = oferta.text if oferta is not None else ''
            )
            data.append(alimento)
        for alimento in data:
            result.append(alimento.alimento_list())
        return result
    def save_disk(self, database):
        cols = ['nombre', 'imagen', 'precio_peso', 'precio', 'oferta']
        dataFrame = pd.DataFrame(database,cols)
        return dataFrame.to_csv('outputs/carrefour.csv')