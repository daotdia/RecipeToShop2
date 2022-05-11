import SwiftUI
import sharedApp

struct Scafold: View {

    private let caseUses: UseCases
    
    //Obtengo los casos de uso.
    init(
        caseUses: UseCases
    ){
        self.caseUses = caseUses
    }
    
    //Aquí dentro es donde se pinta el contenido de la vista.
	var body: some View {
        HStack{
            
        }
	}
}

