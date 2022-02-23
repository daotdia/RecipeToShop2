class Alimento():
    nombre = ''
    imagen_src = ''
    precio_peso = ''
    precio = ''
    oferta = ''
    
    def build_alimento(nombre, imagen_src, precio_peso, oferta, precio):
        alimento = Alimento()
        alimento.nombre = nombre
        alimento.imagen_src = imagen_src
        alimento.precio_peso = precio_peso
        alimento.oferta = oferta
        alimento.precio = precio
        return alimento
    
    def alimento_list(self):
        list = []
        list.append(self.nombre)
        list.append(self.imagen_src)
        list.append(self.precio_peso)
        list.append(self.precio)
        list.append(self.oferta)
        return list