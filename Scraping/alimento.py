import json

class Alimento():
    nombre = ''
    imagen_src = ''
    precio_peso = ''
    precio = ''
    oferta = ''
    query = ''
    supermercado = ''
    
    def build_alimento(query, nombre, imagen_src, precio_peso, oferta, precio, supermercado):
        alimento = Alimento()
        alimento.query = query
        alimento.nombre = nombre
        alimento.imagen_src = imagen_src
        alimento.precio_peso = precio_peso
        alimento.oferta = oferta
        alimento.precio = precio
        alimento.supermercado = supermercado
        return alimento
    
    def alimento_JSON(self) -> str:
        result = json.dumps(
            {
                'query': self.query,
                'nombre': self.nombre,
                'imagen_src': self.imagen_src,
                'precio_peso': self.precio_peso,
                'precio': self.precio,
                'oferta': self.oferta,
                'supermercado': self.supermercado
            }
        )
        return result