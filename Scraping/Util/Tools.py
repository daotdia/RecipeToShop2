
import carrefour.constantes as consts

def replaceAll(text, replaces) -> str:
        words = text.split()
        words = [word if word not in replaces else '' for word in words]
        return ' '.join(' '.join(words).split())
    
def checkContent(query, nombre) -> bool:
    print("La query sin preposiciones es: " + replaceAll(
                            query.lower().strip(), consts.PREPOSICIONES
                        )  + " y el nombre es: "+ replaceAll(
                            nombre.lower().strip(), consts.PREPOSICIONES
                        )
                    )
    if (
        (
            replaceAll(
                query.lower().strip(), consts.PREPOSICIONES
            )    
            in 
            replaceAll(
                nombre.lower().strip(), consts.PREPOSICIONES
            )
        )
         or 
        (
            replaceAll(
                query.lower().strip(), consts.PREPOSICIONES
            ) 
            in 
            's '.join(
                replaceAll(
                    nombre.lower().strip(), consts.PREPOSICIONES
                ).split()
            )
        )
        or 
        (
            replaceAll(
                query.lower().strip(), consts.PREPOSICIONES
            ) 
            in 
            'es '.join(
                replaceAll(
                    nombre.lower().strip(), consts.PREPOSICIONES
                ).split()
            )
        )
        or 
        (
            replaceAll(
                query.lower().strip(), consts.PREPOSICIONES
            ) 
            in 
            replaceAll( 
                nombre.lower().strip(), consts.PREPOSICIONES
            ) + "s" 
        )
        or
        (
            replaceAll(
                query.lower().strip(), consts.PREPOSICIONES
            ) 
            in 
            replaceAll(
                nombre.lower().strip(), consts.PREPOSICIONES
            ) + "es" 
        )
    ):
        return True
    else:
        return False    